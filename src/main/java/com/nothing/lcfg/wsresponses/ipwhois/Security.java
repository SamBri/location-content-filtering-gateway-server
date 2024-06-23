package com.nothing.lcfg.wsresponses.ipwhois;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Security{
	private boolean anonymous;
	private boolean proxy;
	private boolean vpn;
	private boolean tor;
	private boolean hosting;
}
