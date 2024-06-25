package com.mfholdings.mfholdings.Response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundsHoldingResponse {

    List<Map<String, List<String>>> mutualFundHoldings;

}
