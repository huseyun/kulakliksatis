package com.kulakyokedici.kulakliksitesi.objects.exception;

import org.springframework.http.HttpStatus;

public class InsufficientParametersException extends BaseException
{

	public InsufficientParametersException(String resource)
	{
		super(resource + " kaynağını sorgulamak için yetersiz parametre.", HttpStatus.BAD_REQUEST);
	}

}
