package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class HelloApplication

fun main(args: Array<String>) {
	runApplication<HelloApplication>(*args)
}

@RestController
class HelloController(private val hello: HelloWørld) {

	@GetMapping("/")
	fun hello() = hello.hello()
}

@Component
class HelloWørld {
    fun hello() = "Hello World 2"
}