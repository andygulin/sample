package examples.showcase.guava;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

public class GuavaCollection {

	@Test
	public void test() {
		List<String> list = Lists.newArrayList();
		System.out.println(list);
		list = Lists.newArrayList("aa", "bb", "cc");
		System.out.println(list);

		Set<String> set = Sets.newHashSet();
		System.out.println(set);
		set = Sets.newHashSet("aa", "bb", "cc");
		System.out.println(set);

		Map<String, String> map = Maps.newHashMap();
		System.out.println(map);
		map = ImmutableMap.of("name", "aa");
		System.out.println(map);

		String[] arr = ObjectArrays.newArray(String.class, 10);
		System.out.println(Arrays.toString(arr));
		arr = ObjectArrays.concat("aa", arr);
		System.out.println(Arrays.toString(arr));
	}

	@Test
	public void test2() {
		ImmutableList<String> list = ImmutableList.of("aa", "bb", "cc", "dd");
		System.out.println(list);

		ImmutableSet<String> set = ImmutableSet.of("aa", "bb", "cc", "dd");
		System.out.println(set);

		ImmutableMap<String, String> map = ImmutableMap.of("name", "aa");
		System.out.println(map);
	}

	@Test
	public void test3() {
		Multimap<String, String> multimap = ArrayListMultimap.create();
		multimap.put("name", "aa");
		multimap.put("name", "bb");
		Collection<String> collection = multimap.get("name");
		System.out.println(collection);

		Multiset<Integer> multiset = HashMultiset.create();
		multiset.add(10);
		multiset.add(20);
		multiset.add(30);
		multiset.add(30);
		System.out.println(multiset.count(30));
		System.out.println(multiset.size());

		Table<Integer, Integer, String> table = HashBasedTable.create();
		table.put(1, 1, "aa");
		table.put(1, 2, "bb");
		table.put(1, 3, "cc");
		System.out.println(table.get(1, 1));

		BiMap<Integer, String> biMap = HashBiMap.create();
		biMap.put(1, "aa");
		biMap.put(2, "bb");
		biMap.put(3, "cc");
		System.out.println(biMap.get(1));
		System.out.println(biMap.inverse().get("aa"));

		ClassToInstanceMap<Number> classToInstanceMap = MutableClassToInstanceMap.create();
		classToInstanceMap.put(Integer.class, 11);
		classToInstanceMap.put(Double.class, 22d);
		classToInstanceMap.put(Float.class, 33f);
		System.out.println(classToInstanceMap.getInstance(Integer.class));
	}

	@Test
	public void test4() {
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		ImmutableMultiset<Integer> immutableMultiset = ImmutableMultiset
				.copyOf(Collections2.filter(list, new Predicate<Integer>() {
					@Override
					public boolean apply(Integer input) {
						return input > 5;
					}
				}));
		Optional<ImmutableMultiset<Integer>> optional = Optional.fromNullable(immutableMultiset);
		if (optional.isPresent()) {
			System.out.println(optional.get());
		}
	}

	@Test
	public void test5() {
		List<Integer> list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, 10, 0, 2);
		System.out.println(Ordering.natural().sortedCopy(list));
		System.out.println(Ordering.natural().reverse().sortedCopy(list));
		System.out.println(Ordering.natural().min(list));
		System.out.println(Ordering.natural().max(list));

		list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, null, 0, 2);
		System.out.println(Ordering.natural().nullsLast().sortedCopy(list));
		System.out.println(Ordering.natural().nullsFirst().sortedCopy(list));

		list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, 10, 0, 2);
		Ordering<Integer> ordering = new Ordering<Integer>() {
			@Override
			public int compare(Integer left, Integer right) {
				return left - right;
			}
		};
		System.out.println(ordering.immutableSortedCopy(list));
	}
}