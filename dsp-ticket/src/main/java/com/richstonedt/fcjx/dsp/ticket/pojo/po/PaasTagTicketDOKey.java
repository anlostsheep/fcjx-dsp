package com.richstonedt.fcjx.dsp.ticket.pojo.po;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table paas_tag_tickets
 */
public class PaasTagTicketDOKey {
    /**
     * Database Column Remarks:
     *   工单号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_tickets.ticket_no
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    private Integer ticketNo;

    /**
     * Database Column Remarks:
     *   paas平台用户标签
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column paas_tag_tickets.paas_tag
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    private String paasTag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_tickets.ticket_no
     *
     * @return the value of paas_tag_tickets.ticket_no
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    public Integer getTicketNo() {
        return ticketNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_tickets.ticket_no
     *
     * @param ticketNo the value for paas_tag_tickets.ticket_no
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    public void setTicketNo(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column paas_tag_tickets.paas_tag
     *
     * @return the value of paas_tag_tickets.paas_tag
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    public String getPaasTag() {
        return paasTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column paas_tag_tickets.paas_tag
     *
     * @param paasTag the value for paas_tag_tickets.paas_tag
     *
     * @mbg.generated Sun Apr 26 16:18:12 CST 2020
     */
    public void setPaasTag(String paasTag) {
        this.paasTag = paasTag == null ? null : paasTag.trim();
    }
}