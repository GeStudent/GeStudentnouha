<?php

namespace BackBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function acceuilAction()
    {
        return $this->render('@Back/Default/acceuil.html.twig');
    }
    public function ClubAction()
    {
        return $this->render('@Back/Default/club.html.twig');
    }
}
