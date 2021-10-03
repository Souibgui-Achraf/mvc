package com.sip.ams.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sip.ams.entities.Etudiant;

@RequestMapping("etudiant")
@Controller
public class EtudiantController {

	public List<Etudiant> students = new ArrayList<>();
	{
		students.add(new Etudiant(1, "achraf", "achrafsoubgui@ensi-uma.tn"));
		students.add(new Etudiant(2, "ahmed", "ahmedsoubgui@ensi-uma.tn"));
		students.add(new Etudiant(3, "patrick", "apatricksoubgui@ensi-uma.tn"));
	}

	@RequestMapping("/home")
	public String message(Model model) {
		System.out.println("Bienvenue au Bootcamp");
		String formation = "fullstack";
		String lieu = "Sesame";
		model.addAttribute("training", formation);
		model.addAttribute("location", lieu);
		return "info";
	}

	@RequestMapping("/produits")
	// public String listProduct(Model model) {
	public ModelAndView ListProduct() {
		ModelAndView mv = new ModelAndView();
		List<String> products = new ArrayList<>();
		products.add("voiture");
		products.add("camion");
		// model.addAttribute("produit1",products);
		mv.addObject("produits", products);
		mv.setViewName("products");
		return mv;
	}

	@RequestMapping("/students")
	public ModelAndView listStudent() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("students", students);
		mv.setViewName("student");
		return mv;
	}

	// @GetMapping("/add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addStudentForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addStudent");
		return mv;

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
		public String addStudent(@RequestParam("id") int id, @RequestParam("nomEtudiant") String nom,
				@RequestParam("email") String email) {
			// ModelAndView mv = new ModelAndView();
			// mv.setViewName("listStudents");
			// return mv;
			Etudiant e = new Etudiant(id, nom, email);
			System.out.println(e);
			students.add(e);
			System.out.println(students);
			return "redirect:students";

		}
	@RequestMapping(value ="/delete/{id}", method= RequestMethod.GET)
	public String supprimer(@PathVariable("id") int id) {
		System.out.println(id);
		students.remove(recherche(students,id));
		return "redirect:../students";
	}
	private Etudiant recherche(List<Etudiant> le , int index) {
		for (Etudiant e :le) {
			if (e.getId()==index) {
				return e ;
			}
		}
		return null;
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public ModelAndView updateStudentForm(@PathVariable("id") int id, Model model) {
		ModelAndView mv = new ModelAndView();
		Etudiant e = null;
		e=recherche(students,id);
		mv.addObject("etudiant", e);
		mv.setViewName("updateStudent");
		return mv;

	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String updateStudent(@RequestParam("id") int id, @RequestParam("nom") String nom,
				@RequestParam("email") String email) {
			// ModelAndView mv = new ModelAndView();
			// mv.setViewName("listStudents");
			// return mv;
			Etudiant e = new Etudiant(id, nom, email);
			students.set(indexStudent(id),e);
			
			System.out.println(e);
			System.out.println(students);
			return "redirect:students";

		}
	public int indexStudent(int id) {
		for (int i=0;i<students.size();i++) {
			if (students.get(i).getId()==id) {
				return i;
			}
		}
		return -1;
	}
}