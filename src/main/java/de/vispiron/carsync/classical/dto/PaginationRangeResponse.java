package de.vispiron.carsync.classical.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class PaginationRangeResponse {

	private static final String UNIT = "item";

	private Integer first;

	private Integer last;

	private Integer total;

	public PaginationRangeResponse(String range) {

		Pattern pattern = Pattern.compile(
				"^\\s*(?<name>\\w+)\\s*((?<first>\\d+)\\s*-\\s*(?<last>\\d+)|(?<leftStar>\\*))\\s*/\\s*(?<total"
						+ ">(\\d+|\\*))$");
		Matcher matcher = pattern.matcher(range);
		if (!matcher.find()) {
			throw new RuntimeException("Wrong range format");
		}
		if (matcher.group("first") != null && matcher.group("last") != null) {
			this.first = Integer.parseInt(matcher.group("first"));
			this.last = Integer.parseInt(matcher.group("last"));
			if (this.last < this.first) {
				throw new RuntimeException("the range end must be superior or equal to the range start");
			}
		} else if (matcher.group("leftStar") != null) {
			this.first = null;
			this.last = null;
		}
		if (matcher.group("total").equals("*")) {
			this.total = null;
		} else {
			this.total = Integer.parseInt(matcher.group("total"));
			if (this.last != null && this.last >= this.total) {
				throw new RuntimeException("the range end must be inferior to the total");
			}
		}
	}

	public PaginationRangeResponse(Integer first, Integer last, Integer total) {
		this(first, last);
		this.total = total;
		if (this.last >= this.total) {
			throw new RuntimeException("the range end must be inferior to the total");
		}
	}

	public PaginationRangeResponse(Integer first, Integer last) {
		if (first < 0) {
			throw new RuntimeException("the range start must be positive");
		}
		if (last < 0) {
			throw new RuntimeException("the range end must be positive");
		}
		if (last < first) {
			throw new RuntimeException("the range end must be superior or equal to the range start");
		}
		this.first = first;
		this.first = last;
	}

	public String toString() {
		return PaginationRangeResponse.UNIT + "=" + this.first + "-" + this.last;
	}

	public int getCount() {
		return this.last - this.first + 1;
	}
}
