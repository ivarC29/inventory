package com.allicaihub.inventory.persistence.entity;

public enum OrderStatus {
    APPROVED, // only available if the current status is PENDING_APPROVAL or PENDING_REVIEW.
    CANCELLED, // only available if the current status is PENDING_APPROVAL, APPROVED, ONGOING or PENDING_REVIEW
    CLOSED, // no available if the current status is PENDING_REVIEW. This status can't be updated.
    PENDING, // The PENDING status remains until the purchase order revision is approved.
    ONGOING, // Indicates that a purchase order is being created and is not ready for approval.
    PENDING_REVIEW, // only available if the current status is APPROVED or ONGOING.
    REVIEWED, // Indicates that a purchase order has been reviewed.
    PENDING_APPROVAL, // The default status of a purchase order when you create it. In this state, some default fields are read-only.
}
