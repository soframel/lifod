package org.soframel.lifod.repository.dao;

import java.util.List;

public interface GraphsDao {
	public List<GraphData> getLifespanByBrand(String brand);
}
