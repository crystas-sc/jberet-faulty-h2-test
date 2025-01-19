package org.capps.batch;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.capps.dto.ItemDto;
import org.capps.service.DistanceCalculator;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
@jakarta.inject.Named
public class MyItemProcessor implements jakarta.batch.api.chunk.ItemProcessor {

        @Inject
        DistanceCalculator distanceCalculator;

        @Inject
        ObjectMapper objectMapper;

        @Override
        public ItemDto processItem(Object item) throws Exception {
                System.out.println("Processing item: " + item);

                ItemDto itemDto = objectMapper.readValue(item.toString(), ItemDto.class);
                double distanceCost = distanceCalculator.calculateCostBasedOnDistance(40.7128, -74.0060,
                                Double.valueOf(itemDto.getLat()), Double.valueOf(itemDto.getLng()), 10);
                itemDto = itemDto.toBuilder()
                                .cost(new BigDecimal(Double.toString(distanceCost))
                                                .setScale(2, RoundingMode.HALF_UP))
                                .build();
                System.out.println("distanceCost " + distanceCost);

                return itemDto;
        }
}