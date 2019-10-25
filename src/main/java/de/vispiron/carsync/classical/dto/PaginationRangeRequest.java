package de.vispiron.carsync.classical.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class PaginationRangeRequest {

	private static final String UNIT = "item";

	private int first;

	private int last;

	public PaginationRangeRequest(String range) {

		Pattern pattern = Pattern.compile("^\\s*(?<name>\\w+)\\s*=\\s*(?<first>\\d+)\\s*-\\s*(?<last>\\d+)\\s*$");
		Matcher matcher = pattern.matcher(range);
		if (!matcher.find()) {
			throw new RuntimeException("Wrong range format");
		}
		this.first = Integer.parseInt(matcher.group("first"));
		this.last = Integer.parseInt(matcher.group("last"));
		if (this.last < this.first) {
			throw new RuntimeException("the range end must be superior or equal to the range start");
		}
	}

	public PaginationRangeRequest(int first, int last) {
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
		this.last = last;
	}

	public String toString() {
		return PaginationRangeRequest.UNIT + "=" + this.first + "-" + this.last;
	}

	public int getCount() {
		return last - first + 1;
	}
}
