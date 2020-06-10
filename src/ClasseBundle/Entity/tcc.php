<?php

namespace ClasseBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * tcc
 *
 * @ORM\Table(name="tcc")
 * @ORM\Entity(repositoryClass="ClasseBundle\Repository\tccRepository")

class tcc
{
    /**
     * @var int
     *
     * @ORM\Column(name="idteacher", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")

    private $id;

    /**
     *     @ORM\ManyToOne(targetEntity="ClasseBundle\Entity\cours")
     *     @ORM\JoinColumn(name="idcours",referencedColumnName="id")

    private $idcours;

    /**
     *     @ORM\ManyToOne(targetEntity="ClasseBundle\Entity\classs")
     *     @ORM\JoinColumn(name="idclass",referencedColumnName="id")

    private $idclass;


    /**
     * Get id
     *
     * @return int

    public function getId()
    {
        return $this->id;
    }

    /**
     * Set idcours
     *
     * @param integer $idcours
     *
     * @return tcc

    public function setIdcours($idcours)
    {
        $this->idcours = $idcours;

        return $this;
    }

    /**
     * Get idcours
     *
     * @return int

    public function getIdcours()
    {
        return $this->idcours;
    }

    /**
     * Set idclass
     *
     * @param integer $idclass
     *
     * @return tcc

    public function setIdclass($idclass)
    {
        $this->idclass = $idclass;

        return $this;
    }

    /**
     * Get idclass
     *
     * @return int

    public function getIdclass()
    {
        return $this->idclass;
    }
}
*/
