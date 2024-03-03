package lu.dave.finance.payment.entity.enumaration;

import lombok.ToString;

@ToString
//replace by ref db table if larger model
public enum ContributorType {
    OWNER, MANAGER, VIEWER, PARTICIPANT
}
