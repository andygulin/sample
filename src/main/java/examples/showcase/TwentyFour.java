package examples.showcase;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwentyFour {

	private static final Logger logger = LogManager.getLogger(TwentyFour.class);

	private int[] aArray = new int[2];
	private int[] bArray = new int[2];
	private int[] cArray = new int[2];
	private int[] dArray = new int[2];

	public static void main(String[] args) {
		new TwentyFour().dispose(1, 1, 5, 5);
	}

	public void dispose(int a, int b, int c, int d) {
		aArray[0] = a;
		aArray[1] = 1;
		bArray[0] = b;
		bArray[1] = 1;
		cArray[0] = c;
		cArray[1] = 1;
		dArray[0] = d;
		dArray[1] = 1;
		core(aArray, bArray, cArray, dArray);
	}

	public int[] plus(int[] a, int[] b) {

		if (0 == a[1]) {
			return new int[] { 0, 0 };
		}
		if (0 == b[1]) {
			return new int[] { 0, 0 };
		}
		int denominator = leaseCommonMultiple(a[1], b[1]);

		return new int[] { denominator / a[1] * a[0] + denominator / b[1] * b[0], denominator };
	}

	public int[] reduce(int[] a, int[] b) {
		if (0 == a[1]) {
			return new int[] { 0, 0 };
		}
		if (0 == b[1]) {
			return new int[] { 0, 0 };
		}
		int denominator = leaseCommonMultiple(a[1], b[1]);

		return new int[] { denominator / a[1] * a[0] - denominator / b[1] * b[0], denominator };
	}

	public int[] multiply(int[] a, int[] b) {
		return new int[] { a[0] * b[0], a[1] * b[1] };
	}

	public int[] divide(int[] a, int[] b) {
		return new int[] { a[0] * b[1], a[1] * b[0] };
	}

	public int leaseCommonMultiple(int a, int b) {
		return a * b / greatestCommonDivisor(a, b);
	}

	public int greatestCommonDivisor(int a, int b) {
		if (0 == a && 0 == b) {
			return 1;
		}
		if (0 == a) {
			return b;
		}
		if (0 == b) {
			return a;
		}
		if (a > b) {
			int c = a;
			a = b;
			b = c;
		}
		for (int c = a % b; c > 0; c = a % b) {
			a = b;
			b = c;
		}
		return b;
	}

	public int[] dispose(int i, int a[], int[] b) {
		if (i == 0) {
			return plus(a, b);
		} else if (i == 1) {
			return reduce(a, b);
		} else if (i == 2) {
			return multiply(a, b);
		} else {
			return divide(a, b);
		}
	}

	public int[] number(int i, int[] a, int[] b, int[] c, int[] d) {
		if (i == 0) {
			return a;
		} else if (i == 1) {
			return b;
		} else if (i == 2) {
			return c;
		} else if (i == 3) {
			return d;
		} else {
			return new int[] { 0, 1 };
		}
	}

	private void core(int[] a, int[] b, int[] c, int[] d) {
		Map<Integer, Integer> map = newHashMap();

		int count = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int m = 0; m < 4; m++) {
						for (int n = 0; n < 4; n++) {
							if (n == m)
								continue;
							for (int o = 0; o < 4; o++) {
								if (o == m || o == n)
									continue;
								for (int p = 0; p < 4; p++) {
									if (p == m || p == n || p == o)
										continue;
									int[] result1 = dispose(k,
											dispose(j, dispose(i, number(m, a, b, c, d), number(n, a, b, c, d)),
													number(o, a, b, c, d)),
											number(p, a, b, c, d));
									int[] result2 = dispose(k,
											dispose(i, number(m, a, b, c, d),
													dispose(j, number(n, a, b, c, d), number(o, a, b, c, d))),
											number(p, a, b, c, d));
									int[] result3 = dispose(i, number(m, a, b, c, d), dispose(j, number(n, a, b, c, d),
											dispose(k, number(o, a, b, c, d), number(p, a, b, c, d))));
									int[] result4 = dispose(i, number(m, a, b, c, d),
											dispose(k, dispose(j, number(n, a, b, c, d), number(o, a, b, c, d)),
													number(p, a, b, c, d)));
									int[] result5 = dispose(j, dispose(i, number(m, a, b, c, d), number(n, a, b, c, d)),
											dispose(k, number(o, a, b, c, d), number(p, a, b, c, d)));
									if (0 != result1[1]) {
										if (result1[0] % result1[1] == 0) {
											if (result1[0] / result1[1] == 24) {
												if ((!map.containsKey(new Integer(i)))
														|| (!map.containsKey(new Integer(j)))
														|| (!map.containsKey(new Integer(k)))) {
													map.put(new Integer(i), new Integer(i));
													map.put(new Integer(j), new Integer(j));
													map.put(new Integer(k), new Integer(k));
													count += 1;
													logger.info("((" + printNumber(m) + printSymbol(i) + printNumber(n)
															+ ")" + printSymbol(j) + printNumber(o) + ")"
															+ printSymbol(k) + printNumber(p));
												}
											}
										}
									}
									if (0 != result2[1]) {
										if (result2[0] % result2[1] == 0) {
											if (result2[0] / result2[1] == 24) {
												if ((!map.containsKey(new Integer(i)))
														|| (!map.containsKey(new Integer(j)))
														|| (!map.containsKey(new Integer(k)))) {
													map.put(new Integer(i), new Integer(i));
													map.put(new Integer(j), new Integer(j));
													map.put(new Integer(k), new Integer(k));
													count += 1;
													logger.info("(" + printNumber(m) + printSymbol(i) + "("
															+ printNumber(n) + printSymbol(j) + printNumber(o) + "))"
															+ printSymbol(k) + printNumber(p));
												}
											}
										}
									}
									if (0 != result3[1]) {
										if (result3[0] % result3[1] == 0) {
											if (result3[0] / result3[1] == 24) {
												if ((!map.containsKey(new Integer(i)))
														|| (!map.containsKey(new Integer(j)))
														|| (!map.containsKey(new Integer(k)))) {
													map.put(new Integer(i), new Integer(i));
													map.put(new Integer(j), new Integer(j));
													map.put(new Integer(k), new Integer(k));
													count += 1;
													logger.info(printNumber(m) + printSymbol(i) + "(" + printNumber(n)
															+ printSymbol(j) + "(" + printNumber(o) + printSymbol(k)
															+ printNumber(p) + "))");
												}
											}
										}
									}
									if (0 != result4[1]) {
										if (result4[0] % result4[1] == 0) {
											if (result4[0] / result4[1] == 24) {
												if ((!map.containsKey(new Integer(i)))
														|| (!map.containsKey(new Integer(j)))
														|| (!map.containsKey(new Integer(k)))) {
													map.put(new Integer(i), new Integer(i));
													map.put(new Integer(j), new Integer(j));
													map.put(new Integer(k), new Integer(k));
													count += 1;
													logger.info(printNumber(m) + printSymbol(i) + "((" + printNumber(n)
															+ printSymbol(j) + printNumber(o) + ")" + printSymbol(k)
															+ printNumber(p) + ")");
												}
											}
										}
									}
									if (0 != result5[1]) {
										if (result5[0] % result5[1] == 0) {
											if (result5[0] / result5[1] == 24) {
												if ((!map.containsKey(new Integer(i)))
														|| (!map.containsKey(new Integer(j)))
														|| (!map.containsKey(new Integer(k)))) {
													map.put(new Integer(i), new Integer(i));
													map.put(new Integer(j), new Integer(j));
													map.put(new Integer(k), new Integer(k));
													count += 1;
													logger.info("(" + printNumber(m) + printSymbol(i) + printNumber(n)
															+ ")" + printSymbol(j) + "(" + printNumber(o)
															+ printSymbol(k) + printNumber(p) + ")");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		logger.info("共 " + count + " 种方法");
	}

	public int printNumber(int i) {
		if (i == 0) {
			return aArray[0];
		}
		if (i == 1) {
			return bArray[0];
		}
		if (i == 2) {
			return cArray[0];
		}
		if (i == 3) {
			return dArray[0];
		}
		return 0;
	}

	public String printSymbol(int i) {

		if (i == 0) {
			return " + ";
		}
		if (i == 1) {
			return " - ";
		}
		if (i == 2) {
			return " * ";
		}
		if (i == 3) {
			return " / ";
		}
		return "";
	}
}
