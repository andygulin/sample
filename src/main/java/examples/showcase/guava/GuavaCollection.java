package examples.showcase.guava;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.*;

public class GuavaCollection {

    private static final Logger logger = LogManager.getLogger(GuavaCollection.class);

    @Test
    public void test() {
        List<String> list = Lists.newArrayList();
        logger.info(list);
        list = Lists.newArrayList("aa", "bb", "cc");
        logger.info(list);

        Set<String> set = Sets.newHashSet();
        logger.info(set);
        set = Sets.newHashSet("aa", "bb", "cc");
        logger.info(set);

        Map<String, String> map = Maps.newHashMap();
        logger.info(map);
        map = ImmutableMap.of("name", "aa");
        logger.info(map);

        String[] arr = ObjectArrays.newArray(String.class, 10);
        logger.info(Arrays.toString(arr));
        arr = ObjectArrays.concat("aa", arr);
        logger.info(Arrays.toString(arr));
    }

    @Test
    public void test2() {
        ImmutableList<String> list = ImmutableList.of("aa", "bb", "cc", "dd");
        logger.info(list);

        ImmutableSet<String> set = ImmutableSet.of("aa", "bb", "cc", "dd");
        logger.info(set);

        ImmutableMap<String, String> map = ImmutableMap.of("name", "aa");
        logger.info(map);
    }

    @Test
    public void test3() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("name", "aa");
        multimap.put("name", "bb");
        Collection<String> collection = multimap.get("name");
        logger.info(collection);

        Multiset<Integer> multiset = HashMultiset.create();
        multiset.add(10);
        multiset.add(20);
        multiset.add(30);
        multiset.add(30);
        logger.info(multiset.count(30));
        logger.info(multiset.size());

        Table<Integer, Integer, String> table = HashBasedTable.create();
        table.put(1, 1, "aa");
        table.put(1, 2, "bb");
        table.put(1, 3, "cc");
        logger.info(table.get(1, 1));

        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(1, "aa");
        biMap.put(2, "bb");
        biMap.put(3, "cc");
        logger.info(biMap.get(1));
        logger.info(biMap.inverse().get("aa"));

        ClassToInstanceMap<Number> classToInstanceMap = MutableClassToInstanceMap.create();
        classToInstanceMap.put(Integer.class, 11);
        classToInstanceMap.put(Double.class, 22d);
        classToInstanceMap.put(Float.class, 33f);
        logger.info(classToInstanceMap.getInstance(Integer.class));
    }

    @Test
    public void test4() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ImmutableMultiset<Integer> immutableMultiset = ImmutableMultiset
                .copyOf(Collections2.filter(list, input -> input > 5));
        Optional<ImmutableMultiset<Integer>> optional = Optional.fromNullable(immutableMultiset);
        if (optional.isPresent()) {
            logger.info(optional.get());
        }
    }

    @Test
    public void test5() {
        List<Integer> list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, 10, 0, 2);
        logger.info(Ordering.natural().sortedCopy(list));
        logger.info(Ordering.natural().reverse().sortedCopy(list));
        logger.info(Ordering.natural().min(list));
        logger.info(Ordering.natural().max(list));

        list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, null, 0, 2);
        logger.info(Ordering.natural().nullsLast().sortedCopy(list));
        logger.info(Ordering.natural().nullsFirst().sortedCopy(list));

        list = Lists.newArrayList(9, 4, 6, 1, 3, 5, 8, 10, 0, 2);
        Ordering<Integer> ordering = new Ordering<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                return left - right;
            }
        };
        logger.info(ordering.immutableSortedCopy(list));
    }
}