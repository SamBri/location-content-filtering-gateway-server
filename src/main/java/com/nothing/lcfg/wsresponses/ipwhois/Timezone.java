package com.nothing.lcfg.wsresponses.ipwhois;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
@NoArgsConstructor
public class Timezone{
	private String id;
	private String abbr;
	private boolean is_dst;
	private int offset;
	private String utc;
	private Date current_time;
}