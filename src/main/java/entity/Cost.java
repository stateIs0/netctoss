package entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Cost implements Serializable {
	private Integer costID;
	private String  name;
	//基本时长:套餐内包含的时间  Yi duration时间[djʊ'reɪʃ(ə)n]
	private Integer baseDuration;
	//基本费用:套餐的初始费用.
	private Double baseCost;
	//单位费用:超出套餐的部分每分钟的费用.cost译为花费
	private Double unitCost;
	//状态:0-开通;1-暂停.译为:状态
	private String status;
	//资费说明:description描述
	private String descr;
	//创建时间:
	private Timestamp creatime;
	//开通时间
	private Timestamp startime;
	//资费类型:1-包月;2-套餐;3-计时
	private String costType;
	public Integer getCostID() {
		return costID;
	}
	public void setCostID(Integer costID) {
		this.costID = costID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBaseDuration() {
		return baseDuration;
	}
	public void setBaseDuration(Integer baseDuration) {
		this.baseDuration = baseDuration;
	}
	public Double getBaseCost() {
		return baseCost;
	}
	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Timestamp getCreatime() {
		return creatime;
	}
	public void setCreatime(Timestamp creatime) {
		this.creatime = creatime;
	}
	public Timestamp getStartime() {
		return startime;
	}
	public void setStartime(Timestamp startime) {
		this.startime = startime;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	
	
	
	
	
	
}
