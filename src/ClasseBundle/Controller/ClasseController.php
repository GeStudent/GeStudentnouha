<?php

namespace ClasseBundle\Controller;

use AppBundle\Entity\User;
use ClasseBundle\Entity\classs;
use ClasseBundle\Entity\cours;
use ClasseBundle\Form\classsType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Bundle\SwiftmailerBundle\SwiftmailerBundle;
use Swift_Mailer;
use Swift_SmtpTransport;


class ClasseController extends Controller
{





    function AjoutClassAction(Request $request)
    {
        $Classe = new classs();

        $Form = $this->createForm(classsType::class, $Classe)->handleRequest($request);
        if ($Form->isValid()) {
            $em = $this->getDoctrine()->getManager();

            $em->persist($Classe);
            $em->flush();

            return $this->redirect($this->generateUrl('Listlass'));
        }
        return $this->render('@Classe/classs/AjoutClasss.html.twig', array('form' => $Form->createView()));

    }


    public function ListClassAction()
    {
        $em = $this->getDoctrine()->getManager();
        $classs = $em->getRepository(classs::class)->findAll();
        $cours = $em->getRepository(cours::class)->findAll();
        return $this->render('@Classe/classs/listclasss.html.twig', array('cours' => $cours, 'classs' => $classs));
    }

    public function AffectCourAction($idclass, $name)
    {
        $em = $this->getDoctrine()->getManager();
        $cours = $em->getRepository(cours::class)->findOneBy(array('name' => $name));
        $class = $em->getRepository(classs::class)->find($idclass);

        $cours->addClass($class);
        $em->persist($cours);
        $em->flush();
        return $this->redirect($this->generateUrl('Listlass'));
        //return $this->render('@Classe/classs/listlass.html.twig');

    }

    public function ListcourclasseAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $Class = $em->getRepository(classs::class)->find($id);

        $cours = $Class->getCour();

        return $this->render('@Classe/classs/coursclasse.html.twig', array('Class' => $Class, 'cours' => $cours));
    }


    public function deleteClasseAction(Request $request, $id)
    {
        $Classe = new classs();
        $em = $this->getDoctrine()->getManager();
        $Classe = $em->getRepository(classs::class)->find($id);
        $em->remove($Classe);
        $em->flush();
        //return $this->redirect('Listlass');
        return $this->redirect($this->generateUrl('Listlass'));


    }


    Public function UpdateClasseAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $Classe = $em->getRepository(classs::class)->find($id);
        $Form = $this->createForm(classsType::class, $Classe)->handleRequest($request);
        if ($Form->isSubmitted()) {


            $em->persist($Classe);
            $em->flush();
            return $this->redirect($this->generateUrl('Listlass'));
        }
        return $this->render('@Classe/classs/UpdateClasse.html.twig', array('form' => $Form->createView()));


    }

    public function AffectClassAction($idStudent, $nameC)
    {
        $em = $this->getDoctrine()->getManager();
        $classC = $em->getRepository(classs::class)->findOneBy(array('nameC' => $nameC));
        $student = $em->getRepository(User::class)->find($idStudent);

        $classC->addStudent($student);
        $em->persist($classC);
        $em->flush();
        return $this->redirectToRoute('ListStudents');

    }
/*    public function AffectClassMobileAction($idStudent, $nameC)
    {



    }
*/
    public function FrontuserAction()
    {
        $em = $this->getDoctrine()->getManager();
        // $id=$this->getUser()->getId();

        //$students = $em->getRepository(User::class)->find($id);
        //$ClassC = $students->getClass();

        $ClassE = $this->getUser()->getclass();
        $ClassC = $this->getUser()->getclass();

        $id = 0;
        foreach ($ClassC as $class) {
            $id = $class->getId();
        }
        $ClassC = $em->getRepository(classs::class)->find($id);
        $cours = $ClassC->getCour();
        return $this->render('@Classe/classs/front.html.twig', array('students' => $this->getUser(), 'class' => $ClassC, 'cours' => $cours, 'ClassE' => $ClassE));
    }


    public function ListStudentAction($id)
    {
        $em = $this->getDoctrine()->getManager();

        $ClassC = $em->getRepository(classs::class)->find($id);
        $students = $ClassC->getStudents();

        return $this->render('@Classe/classs/ListStudents.html.twig', array('students' => $students, 'class' => $ClassC));
    }


    public function supprimerstudentAction($idStudent, $id)
    {

        $em = $this->getDoctrine()->getManager();
        $classC = $em->getRepository(classs::class)->find($id);

        $Student = $em->getRepository(User::class)->find($idStudent);

        $classC->removeStudent($Student);

        $em->persist($classC);

        $em->flush();
        //  return $this->redirectToRoute('ListStudent');

        return $this->redirect($this->generateUrl('ListStudent', array('id' => $classC->getId())));


    }

    public function pdfAction($id)
    {

        $em = $this->getDoctrine()->getManager();
        $ClassC = $em->getRepository(classs::class)->find($id);
        $students = $ClassC->getStudents();
        $html = $this->renderView('@Classe/classs/pdf.html.twig', array('students' => $students, 'class' => $ClassC));

        return new PdfResponse(
            $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
            'file.pdf'
        );

    }
    public  function SendMailCoursAction($idClass ,$idCour,Swift_Mailer $mailer=null)
    {

        $cours= new cours();
        $em=$this->getDoctrine()->getManager();
        $cours=$em->getRepository(cours::class)->find($idCour);

        $classe=$em->getRepository(classs::class)->find($idClass);
        $students=$classe->getStudents();

        foreach ($students as $user)
        {

            $transport = Swift_SmtpTransport::newInstance('smtp.googlemail.com', 465, 'ssl')
                ->setUsername('gestudent.plus@gmail.com')//hedha eli neb3ath bih el mail
                ->setPassword('yassinecss1928');
            $mailer = Swift_Mailer::newInstance($transport);
            $message = (new \Swift_Message('New Arrival !')) // hounibadel el subject
                ->setFrom('gestudent.plus@gmail.com')
                ->setTo($user->getEmail()) // hedha el bech iyjih el mail

                ->setContentType("text/html");
            $pdf = $message->embed(\Swift_Image::fromPath( 'uploads/brochures/' . $cours->getBrochureFilename()));
            $message->setBody($this->renderView(
                'emailCours.html.twig',
                array('livre' => $cours, 'pdf' => $pdf, 'user' => $user)
            ));
            $mailer->send($message);
            $this->get('mailer')->send($message);


        }
       return $this->redirectToRoute('Listlass');
    }

    /* public function pdfAction($id)
     {

         $classC = $this->getDoctrine()->getManager()->getRepository(classs::class)->findAll();
         $html = $this->renderView('@Classe/classs/pdf.html.twig', array('classs' =>
             $classC));

         return new PdfResponse(
             $this->get('knp_snappy.pdf')->getOutputFromHtml($html),
             'file.pdf'
         );

     }
 */


    public function ListstudentMobileAction()
    {
        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT c
        FROM AppBundle:User c '
        );
        $cours = $query->getArrayResult();

        $response = new Response(json_encode($cours));
        $response->headers->set('Content-Type', 'application/json');

        return $response;
    }

    public function ListClassMobileAction()
    {
        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery(
            'SELECT c
        FROM ClasseBundle:classs c '
        );
        $cours = $query->getArrayResult();

        $response = new Response(json_encode($cours));
        $response->headers->set('Content-Type', 'application/json');

        return $response;
    }

    public function AddClassMobileAction(Request $request)
    {

        $nom = $request->query->get('nameC');


        $Cours = new classs();
        $Cours->setNameC($nom);


        $em = $this->getDoctrine()->getManager();


        try {
            $em->persist($Cours);
            $em->flush();

        } catch (\Exception $ex) {
            $data = [
                'title' => 'validation error',
                'message' => 'Some thing went Wrong',
                'errors' => $ex->getMessage()
            ];
            $response = new JsonResponse($data, 400);
            return $response;
        }

        return $this->json(array('title' => 'successful', 'message' => "class added successfully"), 200);


    }


    public function DeleteClassMobileAction(Request $request)
    {
        $id = $request->query->get('id');
        $Cours = $this->getDoctrine()->getRepository('ClasseBundle:classs')->findOneById($id);
        if ($Cours) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($Cours);
            $em->flush();
            $response = array("body" => "classe delete");
        } else {
            $response = array("body" => "Error");
        }
        return new JsonResponse($response);
    }


    public function EditClassMobileAction(Request $request)
    {

        $id = $request->query->get('id');
        $em = $this->getDoctrine()->getManager();
        $Cours = $em->getRepository(classs::class)->find($id);
        $name = $request->query->get('NameC');

        $Cours->setNameC($name);

        try {
            $em->persist($Cours);
            $em->flush();
        } catch (\Exception $ex) {
            $data = [
                'title' => 'validation error',
                'message' => 'Some thing went Wrong',
                'errors' => $ex->getMessage()
            ];
            $response = new JsonResponse($data, 400);
            return $response;
        }
        return $this->json(array('title' => 'successful', 'message' => "classe Edited successfully"), 200);
    }


    public function ClassestudentMobileAction($idclass)
    {
        $em = $this->getDoctrine()->getManager();
        $Club = $em->getRepository(classs::class)->find($idclass);
        if (!$Club)
            return new JsonResponse("classe Not Found!!", 400);

        $members = $Club->getStudents();
        $arrayCollection = array();

        foreach ($members as $member) {
            $conn = $this->getDoctrine()->getEntityManager()->getConnection();
            $sql = "SELECT  Cl.nameC, u.firstname  FROM  class cl ,fos_user u WHERE cl.idclass=? AND  u.id=?  ";
            //"select  nameC from class user  where class_idclass=? and user_id=?  ";

            $stmt = $conn->prepare($sql);
            $stmt->bindValue(1, $idclass);
            $stmt->bindValue(2, $member->getId());
            $stmt->execute();
            $this->getDoctrine()->getManager()->flush();
            $result = $stmt->fetchAll();
            /*foreach($result as $idx => $val){
                $post=$result[0]['post'];
            }
            // var_dump($post);die();
            $member->setPostClub($post);
*/
            $arrayCollection[] = array(
                'id' => $member->getId(),
                // 'post' => $member->getPostClub(),
                'firstname' => $member->getFirstname(),
                'lastname' => $member->getLastname(),
                'email' => $member->getEmail(),
                'image' => $member->getImage(),
                'birthDay' => $member->getBirthDay()->format('d-m-Y'),
                //'role' => $member->getRoles(),
                'gender' => $member->getGender(),
                // ... Same for each property you want
            );

        }

        return new JsonResponse($arrayCollection, 200);

    }



}










