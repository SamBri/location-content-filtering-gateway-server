package com.nothing.lcfg;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RootResponse<T> {
	
	private int code;
	private String status;
	private String message;
	private T response;
	private ZonedDateTime timestamp;
	private String requestId;

}
