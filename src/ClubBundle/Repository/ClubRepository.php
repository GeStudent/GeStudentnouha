<?php

namespace ClubBundle\Repository;

/**
 * ClubRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class ClubRepository extends \Doctrine\ORM\EntityRepository
{
    function AffectedClubs(){
        $query=$this->getEntityManager()->createQuery('select c from ClubBundle:Club c where c.etat=1 ');
        return   $query->getResult()  ;


    }
}
