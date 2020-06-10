<?php

namespace ClasseBundle\Entity;


use Doctrine\ORM\Mapping as ORM;
use AppBundle\Entity\User;
use ClasseBundle\Entity\cours;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
/**
 * classs
 *
 * @ORM\Table(name="class")
  * @UniqueEntity(fields="nameC", message="name is already taken.")
 * @ORM\Entity(repositoryClass="ClasseBundle\Repository\classsRepository")
 */
class classs
{
    /**
     * @var int
     *
     * @ORM\Column(name="idclass", type="integer" )
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="nameC", type="string", length=255, unique=true)
     */
    private $nameC;

    /**
     * classs constructor.
     *
     */
    public function __construct()
    {
        $this->students=new ArrayCollection();
    }


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set nameC
     *
     * @param string $nameC
     *
     * @return classs
     */
    public function setNameC($nameC)
    {
        $this->nameC = $nameC;

        return $this;
    }

    /**
     * Get nameC
     *
     * @return string
     */
    public function getNameC()
    {
        return $this->nameC;
    }


    /**
     * Many class have Many students.
     *
     * @ORM\ManyToMany(targetEntity="AppBundle\Entity\User",inversedBy="classs")
     * @ORM\JoinTable(name="classetudiant",
     * joinColumns={@ORM\JoinColumn(name="idclass", referencedColumnName="idclass")},
     * inverseJoinColumns={@ORM\JoinColumn(name="idEtudiant", referencedColumnName="id",)}
     *    )
     *
     */
    private $students;

    public function addStudent(User $student)
    {
        if (!$this->students->contains($student))
            $this->students[] = $student;

        return $this;
    }


    public function removeStudent(User $student)
    {
        if ($this->students->contains($student))
            $this->students->removeElement($student);
        return $this;
    }

    /**
     * @return Collection| User[]
     */

    public function getStudents()
    {
        return $this->students;
    }
    /**
     * Many class have Many cours.
     *
     * @ORM\ManyToMany(targetEntity="ClasseBundle\Entity\cours",inversedBy="classs")
     * @ORM\JoinTable(name="tcc",
     * joinColumns={@ORM\JoinColumn(name="idclass", referencedColumnName="idclass")},
     * inverseJoinColumns={@ORM\JoinColumn(name="idcours", referencedColumnName="idcour",)}
     *    )
     *
     */

    private $cours;

    public function addCour(cours $cour)
    {
        if (!$this->cours->contains($cour))
            $this->cours[] = $cour;

        return $this;
    }


    public function removeCour(cours $cour)
    {
        if ($this->cours->contains($cour))
            $this->cours->removeElement($cour);
        return $this;
    }

    /**
     * @return Collection| cours[]
     */

    public function getCour()
    {
        return $this->cours;
    }



}

