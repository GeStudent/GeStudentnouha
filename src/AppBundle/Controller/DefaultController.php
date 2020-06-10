<?php

namespace AppBundle\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use ClasseBundle\Entity\classs;
use ClasseBundle\Entity\cours;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
//use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use AppBundle\Entity\User;
use Symfony\Component\HttpFoundation\JsonResponse;
use AppBundle\Repository\coursRepository;

class DefaultController extends Controller
{
    /**
     * @Route("/", name="homepage")
     */
    public function indexAction(Request $request)
    {
        // replace this example code with whatever you need
        return $this->render('default/index.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')).DIRECTORY_SEPARATOR,
        ]);
    }

    /**
     * @Route("/home")
     */
    public function RedirectAction()
    {
        $autochecker=$this->container->get('security.authorization_checker');
        if($autochecker->isGranted('ROLE_STUDENT'))
        {
            return $this->render('@Front/Default/acceuil.html.twig');
        }
        elseif ($autochecker->isGranted('ROLE_ADMIN') || $autochecker->isGranted('ROLE_TEACHER'))
        {
            return $this->render('@Back/Default/acceuil.html.twig');
        }
        else
        {
            return $this->render('@Front/Default/acceuil.html.twig');
        }
    }

    public  function ListStudentsAction()
    {

        $em=$this->getDoctrine()->getManager();
        $users=$em->getRepository(User::class)->findAll();

        $class=$em->getRepository(classs::class)->findAll();

        return $this->render('@App/Users/ListStudents.html.twig',array('students'=>$users,'class'=>$class));
    }
    public  function ListTeachersAction()
    {

        $em=$this->getDoctrine()->getManager();
        $users=$em->getRepository(User::class)->findAll();

        return $this->render('@App/Users/ListTeachers.html.twig',array('teachers'=>$users));
    }
















    public function loginMobileAction($username, $password)
    {
        $user_manager = $this->get('fos_user.user_manager');
        $factory = $this->get('security.encoder_factory');
        $data = [
            'type' => 'validation error',
            'title' => 'There was a validation error',
            'errors' => 'username or password invalide'
        ];
        $response = new JsonResponse($data, 400);
        $user = $user_manager->findUserByUsername($username);
        if(!$user)
            return $response;
        $encoder = $factory->getEncoder($user);
        $bool = ($encoder->isPasswordValid($user->getPassword(),$password,$user->getSalt())) ? "true" : "false";
        if($bool=="true")
        {
            $role= $user->getRoles();
            $data=array('type'=>'Login succeed',
                'id'=>$user->getId(),
                'firstname'=>$user->getFirstname(),
                'lastname'=>$user->getLastname(),
                'email'=>$user->getEmail(),
                'image'=>$user->getImage(),
                'birthDay'=>$user->getBirthDay()->format('d-m-Y'),
                'role'=>$user->getRoles(),
                'gender'=>$user->getGender());
            $response = new JsonResponse($data, 200);
            return $response;
        }
        else
        {
            return $response;
        }
        // return array('name' => $bool);
    }











}
