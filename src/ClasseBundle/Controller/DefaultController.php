<?php

namespace ClasseBundle\Controller;

//use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
//use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
//use Symfony\Component\HttpFoundation\Request;

//use Symfony\Component\DependencyInjection\ContainerInterface;
//use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

//use AppBundle\Entity\cours;
//use AppBundle\Repository\coursRepository;

class DefaultController extends Controller
{

    public function indexAction()
    {
        return $this->render('@Classe/classs/AjoutClasss.html.twig');

    }



}
