<?php
// src/AppBundle/Entity/User.php

namespace AppBundle\Entity;

use ClasseBundle\Entity\classs;
use ClubBundle\Entity\Club;
use Doctrine\Common\Collections\ArrayCollection;
use FOS\UserBundle\Model\User as BaseUser;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="fos_user")
 */
class User extends BaseUser
{
    /**
     * @ORM\Id
     * @ORM\Column(type="integer")
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    protected $id;

    /**
     * @ORM\Column(type="string")
     */protected $firstname;
    /**
     * @ORM\Column(type="string")
     */protected $lastname;

    /**
     * @ORM\Column(type="string")
     */
    protected $image ;

    /**
     * @ORM\Column(type="date")
     */
    protected $birthDay ;


    /**
     * @ORM\Column(type="integer")
     */
    protected  $phone ;

    /**
     * @ORM\Column(type="string")
     */
    protected $pays ;
    /**
     * @ORM\Column(type="string")
     */
    protected $adress ;
    /**
     * @ORM\Column(type="string")
     */
    protected $gender ;

    /**
     * @return mixed
     */
    public function getAdress()
    {
        return $this->adress;
    }

    /**
     * @return mixed
     */
    public function getGender()
    {
        return $this->gender;
    }

    /**
     * @return mixed
     */
    public function getPays()
    {
        return $this->pays;
    }



    /**
     * @return mixed
     */
    public function getBirthDay()
    {
        return $this->birthDay;
    }

    /**
     * @return mixed
     */
    public function getPhone()
    {
        return $this->phone;
    }



    /**
     * @return mixed
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * @param mixed $image
     */
    public function setImage($image)
    {
        $this->image = $image;
    }


    /**
     * @return mixed
     */
    public function getFirstname()
    {
        return $this->firstname;
    }

    /**
     * @param mixed $firstname
     */
    public function setFirstname($firstname)
    {
        $this->firstname = $firstname;
    }

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @return mixed
     */
    public function getLastname()
    {
        return $this->lastname;
    }

    /**
     * @param mixed $lastname
     */
    public function setLastname($lastname)
    {
        $this->lastname = $lastname;
    }

    /**
     * @param mixed $birthDay
     */
    public function setBirthDay($birthDay)
    {
        $this->birthDay = $birthDay;
    }

    /**
     * @param mixed $adress
     */
    public function setAdress($adress)
    {
        $this->adress = $adress;
    }

    /**
     * @param mixed $phone
     */
    public function setPhone($phone)
    {
        $this->phone = $phone;
    }

    /**
     * @param mixed $gender
     */
    public function setGender($gender)
    {
        $this->gender = $gender;
    }

    /**
     * @param mixed $pays
     */
    public function setPays($pays)
    {
        $this->pays = $pays;
    }

    /**
     * Many members can be applied to Many Clubs.
     *
     * @ORM\ManyToMany(targetEntity="ClubBundle\Entity\Club",inversedBy="User")
     *
     */

    private $club;



    public function __construct()
    {
        parent::__construct();
        // your own logic
        $this->club= new ArrayCollection();
        $this->classs=new ArrayCollection();

    }

    public function addClub(Club $club)
    {
        if(!$this->club->contains($club))
            $this->club[] = $club;

        return $this;
    }

    public function removeClub(Club $club)
    {
        if($this->club->contains($club))
            $this->club->removeElement($club);
        return $this;
    }
    /**
     * @return Collection|User[]
     */

    public function getClubs()
    {
        return $this->club;
    }
    /**
     * @return array
     */
    public function getRoles()
    {
        return $this->roles;
    }

    /**
     * @return string
     */

    public function getRole()
    {

        foreach ($this->getRoles() as $role)
        {
            $var=$role;
        }
        return $var;
    }




    /**
     * Many students can be applied to Many class.
     *
     * @ORM\ManyToMany(targetEntity="ClasseBundle\Entity\classs",inversedBy="User")
     * @ORM\JoinTable(name="classetudiant",
     * joinColumns={@ORM\JoinColumn(name="idEtudiant", referencedColumnName="id")},
     * inverseJoinColumns={@ORM\JoinColumn(name="idclass", referencedColumnName="idclass",)}
     *    )
     */

    private $classs;


    public function addClass(classs $classe)
    {
        if(!$this->classs->contains($classe))
            $this->classs[] = $classe;

        return $this;
    }


    public function removeClass(classs $classe)
    {
        if($this->classs->contains($classe))
            $this->classs->removeElement($classe);
        return $this;
    }
    /**
     * @return Collection| classs[]
     */

    public function getclass()
    {
        return $this->classs;
    }





}