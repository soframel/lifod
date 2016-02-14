package org.soframel.lifod.web.rest.dto;

/**
 * A DTO representing a user, with his authorities.
 */
public class StatisticsDTO {

    private Long statistic;

    public StatisticsDTO(Long statistic) {
        this.statistic=statistic;
    }

	public Long getStatistic() {
		return statistic;
	}

	public void setStatistic(Long statistic) {
		this.statistic = statistic;
	}

	@Override
	public String toString() {
		return "StatisticsDTO [statistic=" + statistic + "]";
	}

}
