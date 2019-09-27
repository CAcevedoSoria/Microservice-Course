package com.example.demo.controller;


import com.example.demo.model.Course;
import com.example.demo.service.ServiceCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController

public class CourseController {


	@Autowired
	private ServiceCourse serviceCourse;

	@PostMapping("/")
	public Mono<ResponseEntity<Course>> create(@Valid @RequestBody Course student) {

		return serviceCourse
			.save(student)
			.map(
				p ->
					ResponseEntity.created(URI.create("/api/v1.0/".concat(p.getIdCourse())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(p));
	}



	@GetMapping("/course")
	public Mono<ResponseEntity<Flux<Course>>> findAll() {


		return Mono.just(
			ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(serviceCourse.findAll()));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
		return serviceCourse.findById(id).flatMap(p ->
			serviceCourse.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
			.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

	}



	@GetMapping("nameCourse/{course}")
	public  Mono<ResponseEntity<Course>> findBynameCourse(@PathVariable String nameCourse) {
		return serviceCourse
			.findBynameCourse(nameCourse)
			.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

	}

	@GetMapping("course/{id}")
	public Mono<ResponseEntity<Course>>findByCourseId(@PathVariable String id) {

		return serviceCourse
			.findById(id)
			.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p));

	}




	}



