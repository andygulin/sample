package examples.showcase.sphinx.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

import examples.showcase.sphinx.SphinxClient;
import examples.showcase.sphinx.SphinxException;
import examples.showcase.sphinx.SphinxMatch;
import examples.showcase.sphinx.SphinxResult;

public class SphinxService {

	private SphinxClient client = null;
	private String index = null;
	private int max = 0;

	private SphinxService() {
		Resource resource = new ClassPathResource("sphinx/sphinx.properties");
		Configuration configuration = null;
		try {
			configuration = new PropertiesConfiguration(resource.getFile());
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
		String host = configuration.getString("sphinx.host");
		int port = configuration.getInt("sphinx.port");
		index = configuration.getString("sphinx.index");
		max = configuration.getInt("sphinx.max");
		client = new SphinxClient(host, port);
	}

	private static class SphinxServiceHolder {
		private static SphinxService INSTANCE = new SphinxService();
	}

	public static SphinxService getInstance() {
		return SphinxServiceHolder.INSTANCE;
	}

	private Long[] getMatch(SphinxResult result) {
		SphinxMatch[] matchs = result.matches;
		int matchLen = matchs.length;
		Long[] arrIds = new Long[matchLen];
		int i = 0;
		if (matchLen > 0) {
			for (SphinxMatch match : matchs) {
				arrIds[i++] = match.docId;
			}
		}
		return arrIds;
	}

	private Long[] getRetain(List<Long[]> moreList) {
		List<Long> idsList = Lists.newArrayList();
		for (Long[] list : moreList) {
			for (Long id : list) {
				idsList.add(id);
			}
		}
		int size = idsList.size();
		Long[] idsArr = idsList.toArray(new Long[size]);
		List<Long> result = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (idsArr[i].longValue() == idsArr[j].longValue()) {
					result.add(idsArr[i]);
				}
			}
		}
		return result.toArray(new Long[result.size()]);
	}

	public Long[] getMatchIds(String[] keywords, int limit) {
		return getMatchIds(keywords, limit, SphinxClient.SPH_MATCH_PHRASE, SphinxClient.SPH_SORT_EXTENDED, "@id desc");
	}

	/**
	 * 
	 * @param matchMode
	 *            {@link com.test.demo.sphinx.SphinxClient#SPH_MATCH_PHRASE}
	 * @param sortMode
	 *            {@link com.test.demo.sphinx.SphinxClient#SPH_SORT_EXTENDED}
	 * @return
	 */
	public Long[] getMatchIds(String[] keywords, int limit, int matchMode, int sortMode, String sortBy) {
		int keywordsLen = keywords.length;
		if (keywords == null || keywordsLen == 0) {
			return new Long[0];
		}
		SphinxResult result = null;
		try {
			client.SetMatchMode(matchMode);
			client.SetSortMode(sortMode, sortBy);
			if (limit == 0) {
				client.SetLimits(0, max, max);
			} else {
				client.SetLimits(0, limit, max);
			}
			List<Long[]> moreList = Lists.newArrayListWithCapacity(keywordsLen);
			if (keywordsLen > 1) {
				for (String kw : keywords) {
					result = client.Query(kw, index);
					moreList.add(getMatch(result));
				}
				return getRetain(moreList);
			} else if (keywordsLen == 1) {
				result = client.Query(keywords[0], index);
				return getMatch(result);
			}
		} catch (SphinxException e) {
		}
		return new Long[0];
	}

}
