<?php

namespace FrontBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function acceuilAction()
    {
        return $this->render('@Front/Default/acceuil.html.twig');
    }
    public function aboutAction()
    {
        return $this->render('@Front/Default/about.html.twig');
    }
    public function clubAction()
    {
        return $this->render('@Front/Default/club.html.twig');
    }
}
