package com.strange.conversorDeMonedas.modules;

import java.util.Map;

public record Divisa(Map<String, Double> conversion_rates) {
}
