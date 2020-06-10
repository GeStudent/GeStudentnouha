<?php

namespace ClubBundle\Controller;

use ClubBundle\Entity\Club;
use ClubBundle\Form\ClubType;
use EspritParcBundle\Entity\Modele;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\File\UploadedFile;


class ClubController extends Controller
{
    public  function  AjoutClubAction(Request $request)
    {
        $Club=new Club();
        $user =$this->getUser();
        $Club->setIdPresident($user->getId());
        $Club->setDate(new \DateTime('now'));
        $Club->setNombrePlace(50);
        $Club->setEtat(0);
        $Form = $this->createForm(ClubType::class,$Club)->handleRequest($request);
        if ($Form->isValid()){
            $em=$this->getDoctrine()->getManager();
            /** @var UploadedFile $file */
            $file = $Club->getImage();
            $filename= md5(uniqid()) . '.' . $file->guessExtension();
            $file->move($this->getParameter('photos_directory'), $filename);

            $Club->setImage($filename);
            $em->persist($Club);
            $em->flush();

           return $this->redirect($this->generateUrl('AfficheClub'));
        }
       return $this->render('@Club/Club/AjoutClub.html.twig',array('form'=>$Form->createView()));
       // return $this->redirect($this->generateUrl('club_homepage'));

    }

    public function ListClubAction()
    {
        $em=$this->getDoctrine()->getManager();
        $Club=$em->getRepository(Club::class)->findAll();
        return $this->render('@Club/Club/ListClub.html.twig',array('clubs'=>$Club));
    }
    public function  RefuseClubAction(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Club=$em->getRepository(Club::class)->find($id);
        $em->remove($Club);
        $em->flush();

        return $this->redirectToRoute('ListClub');
    }
    public  function AccpetClubAction(Request $request,$id)
    {
        $em=$this->getDoctrine()->getManager();
        $Club=$em->getRepository(Club::class)->find($id);


        if($Club->getEtat()==0)
        {
            $Club->setEtat(1);
            $em=$this->getDoctrine()->getManager();
            $em->persist($Club);
            $em->flush();
            $this->addFlash('success', 'Club Accpted!');

        }
        else
        {
            $this->addFlash('alert', 'Club is aleardy accpted!');

        }
        return $this->redirect($this->generateUrl('ListClub'));


    }
    public function  AfficheClubAction()
    {

        $em=$this->getDoctrine()->getManager();
        $Club=$em->getRepository(Club::class)->AffectedClubs();
        return $this->render('@Club/Club/AfficheClub.html.twig',array('clubs'=>$Club));;

    }

    public function  JoinClubAction(int $idClub)
    {
        $user =$this->getUser();
        $em=$this->getDoctrine()->getManager();
        $Club=$em->getRepository(Club::class)->find($idClub);
        $Club->addMember($user);
        $em->persist($Club);
        $em->flush();

       return $this->redirect($this->generateUrl('AfficheClub'));



    }

}
