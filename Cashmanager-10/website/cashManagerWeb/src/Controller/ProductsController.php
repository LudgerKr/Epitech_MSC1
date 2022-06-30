<?php

namespace App\Controller;

use App\Entity\Products;
use App\Form\ProductsType;
use App\Repository\ProductsRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProductsController extends AbstractController
{
    /**
     * @Route("/products", name="products")
     * @param ProductsRepository $repo
     * @return mixed
     */
    public function index(ProductsRepository $repo)
    {
        $Products = $repo->findAll();
        return $this->render('products/index.html.twig', [
            'controller_name' => 'ProductsController',
            'products' => $Products,
        ]);
    }

    /**
     * @Route("/", name="home")
     */
    public function home()
    {
        return $this->render('products/home.html.twig');
    }

    /**
     * @Route ("/products/new", name="products_create")
     * @Route ("products/{id}/edit", name="products_edit")
     * @param Products|null $Products
     * @param Request $request
     * @param EntityManagerInterface $em
     * @return RedirectResponse|Response
     */
    public function form(Products $Products = null, Request $request, EntityManagerInterface $em)
    {
        if (!$Products) {
             $Products = new Products();
        }

        $form = $this->createForm(ProductsType::class, $Products);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            if(!$Products->getId()) {
                $Products->setCreationDate(new \DateTime());
            } else {
                $Products->setModificationDate(new \DateTime());
            }

            $em->persist($Products);
            $em->flush();

            return $this->redirectToRoute('products_show', ['id' => $Products->getId()]);
        }

        return $this->render('products/create.html.twig',[
            'formProducts' => $form->createView(),
            'editMode' => $Products->getId() !== null
            ]);
    }

    /**
     * @Route ("/products/{id}", name="products_show");
     * @param Products $Products
     * @return RedirectResponse|Response
     */
    public function show(Products $Products)
    {
        return $this->render('products/show.html.twig', [
            'product' => $Products
        ]);
    }
}
