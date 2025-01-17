/*
 *  Copyright (c) 2022 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - improvements
 *
 */

package org.eclipse.edc.connector.service.dataaddress;

import org.eclipse.edc.spi.types.domain.DataAddress;
import org.eclipse.edc.validator.spi.DataAddressValidator;
import org.junit.jupiter.api.Test;

import static org.eclipse.edc.junit.assertions.AbstractResultAssert.assertThat;
import static org.eclipse.edc.spi.types.domain.HttpDataAddress.HTTP_DATA;

class DataAddressValidatorImplTest {

    private final DataAddressValidator validator = new DataAddressValidatorImpl();

    @Test
    void shouldPass_whenTypeDoesNotNeedValidation() {
        var dataAddress = DataAddress.Builder.newInstance()
                .property("type", "any")
                .build();

        var result = validator.validate(dataAddress);

        assertThat(result).isSucceeded();
    }

    @Test
    void shouldPass_whenHttpDataIsValid() {
        var dataAddress = DataAddress.Builder.newInstance()
                .property("type", HTTP_DATA)
                .property("baseUrl", "http://this.is/valid/url")
                .build();

        var result = validator.validate(dataAddress);

        assertThat(result).isSucceeded();
    }

    @Test
    void shouldFail_whenHttpDataBaseUrlNotValid() {
        var dataAddress = DataAddress.Builder.newInstance()
                .property("type", HTTP_DATA)
                .property("baseUrl", "not-a-valid-url")
                .build();

        var result = validator.validate(dataAddress);

        assertThat(result).isFailed();
    }
}
