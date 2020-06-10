<?php

namespace ClasseBundle\Entity;


use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\HttpFoundation\File\File;

use Vich\UploaderBundle\Mapping\Annotation as Vich;


/**
 * cours
 *@Vich\Uploadable
 * @ORM\Table(name="cours")
 * @ORM\Entity(repositoryClass="ClasseBundle\Repository\coursRepository")
 * @UniqueEntity(fields="lesson", message="lesson  is already taken.")
 */
class cours
{
    /**
     * @var int
     *
     * @ORM\Column(name="idcour", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="name", type="string", length=255)
     */
    private $name;

    /**
     * @var string
     *
     * @ORM\Column(name="lesson", type="string", length=255, unique=true)
     */
    private $lesson;

    /**
     * @var string
     *
     * @ORM\Column(name="duration", type="string",length=255)
     */
    private $duration;

    /**
     *@ORM\Column(name="file", type="string",length=255)
     * @Vich\UploadableField(mapping="user_contracts", fileNameProperty="name")
     * @var File
     */
    private $brochureFilename;







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
     * Set name
     *
     * @param string $name
     *
     * @return cours
     */
    public function setName($name)
    {
        $this->name = $name;

        return $this;
    }





    /**
     * Set brochureFilename
     *
     * @param string $brochureFilename
     *
     * @return cours
     */
    public function setBrochureFilename($brochureFilename)
    {
        $this->brochureFilename = $brochureFilename;

        return $this;
    }

    /**
     * Get brochureFilename
     *
     * @return string
     */
    public function getBrochureFilename()
    {
        return $this->brochureFilename;
    }

    /**
     * Get name
     *
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Set lesson
     *
     * @param string $lesson
     *
     * @return cours
     */
    public function setLesson($lesson)
    {
        $this->lesson = $lesson;

        return $this;
    }

    /**
     * Get lesson
     *
     * @return string
     */
    public function getLesson()
    {
        return $this->lesson;
    }

    /**
     * Set duration
     *
     * @param integer $duration
     *
     * @return cours
     */
    public function setDuration($duration)
    {
        $this->duration = $duration;

        return $this;
    }

    /**
     * Get duration
     *
     * @return int
     */
    public function getDuration()
    {
        return $this->duration;
    }

    /**
     * Many cours can be applied to Many class.
     *
     * @ORM\ManyToMany(targetEntity="ClasseBundle\Entity\classs",inversedBy="cours")
     * @ORM\JoinTable(name="tcc",
     * joinColumns={@ORM\JoinColumn(name="idcours", referencedColumnName="idcour")},
     * inverseJoinColumns={@ORM\JoinColumn(name="idclass", referencedColumnName="idclass",)}
     *    )
     */
    private $classs;


    public function addClass(classs $class)
    {
        if(!$this->classs->contains($class))
            $this->classs[] = $class;

        return $this;
    }


    public function removeClass(classs $class)
    {
        if($this->classs->contains($class))
            $this->classs->removeElement($class);
        return $this;
    }
    /**
     * @return Collection| classs[]
     */

    public function getClass()
    {
        return $this->classs;
    }
















}

