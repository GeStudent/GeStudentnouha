<?php

namespace ClasseBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use AppBundle\Entity\cours;
use Vich\UploaderBundle\Form\Type\VichFileType;

use Symfony\Component\Validator\Constraints\File;

use Symfony\Component\Form\Extension\Core\Type\FileType;

class coursType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('name')
            ->add('lesson')
            ->add('duration')->add('brochureFilename', VichFileType::class, [
                //'required' => false,
                'allow_delete' => true,
               // 'download_uri' => '...',
                //'download_label' => '...',
                //'asset_helper' => true,
            ]);
        ;
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ClasseBundle\Entity\cours'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'classebundle_cours';
    }


}
