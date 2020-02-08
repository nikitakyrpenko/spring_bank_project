package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.domain.impl.Charge;

public interface InterestChargeable {

    Charge processCharge();
}
