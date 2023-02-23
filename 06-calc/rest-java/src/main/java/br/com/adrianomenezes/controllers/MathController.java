package br.com.adrianomenezes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianomenezes.exceptions.UnsupportedMathOperationException;
import br.com.adrianomenezes.math.SimpleMath;
import br.com.adrianomenezes.converters.NumberConverter;

@RestController
@RequestMapping(value = "/math")
public class MathController {

	private SimpleMath math =  new SimpleMath();

	@GetMapping(value = "/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable(value="numberOne") String num1,
			@PathVariable(value="numberTwo") String num2)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) || ! NumberConverter.isNumeric(num2) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.sum(NumberConverter.convertToDouble(num1), NumberConverter.convertToDouble(num2));
	}
	
	@GetMapping(value = "/subtraction/{numberOne}/{numberTwo}")
	public Double subtraction(@PathVariable(value="numberOne") String num1,
			@PathVariable(value="numberTwo") String num2)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) || ! NumberConverter.isNumeric(num2) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.subtraction	(NumberConverter.convertToDouble(num1), NumberConverter.convertToDouble(num2));
	}
	
	@GetMapping(value = "/multiply/{numberOne}/{numberTwo}")
	public Double multiply(@PathVariable(value="numberOne") String num1,
			@PathVariable(value="numberTwo") String num2)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) || ! NumberConverter.isNumeric(num2) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.multiply(NumberConverter.convertToDouble(num1), NumberConverter.convertToDouble(num2));
	}
	
	@GetMapping(value = "/division/{numberOne}/{numberTwo}")
	public Double division(@PathVariable(value="numberOne") String num1,
			@PathVariable(value="numberTwo") String num2)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) || ! NumberConverter.isNumeric(num2) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.division(NumberConverter.convertToDouble(num1), NumberConverter.convertToDouble(num2));
	}
	
	@GetMapping(value = "/average/{numberOne}/{numberTwo}")
	public Double average(@PathVariable(value="numberOne") String num1,
			@PathVariable(value="numberTwo") String num2)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) || ! NumberConverter.isNumeric(num2) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.average(NumberConverter.convertToDouble(num1), NumberConverter.convertToDouble(num2));
	}
	
	@GetMapping(value = "/sqrt/{numberOne}")
	public Double sqrt(@PathVariable(value="numberOne") String num1)  
		throws Exception {
		if (! NumberConverter.isNumeric(num1) ) {
			throw new UnsupportedMathOperationException("Please set a numeric value");
		}
		return math.sqrt(NumberConverter.convertToDouble(num1));
	}



}
