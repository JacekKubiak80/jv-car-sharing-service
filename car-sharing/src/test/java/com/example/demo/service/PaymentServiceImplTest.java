package com.example.demo.service;

import com.example.demo.dto.PaymentResponseDto;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void getAllPayments_ShouldReturnListOfPayments() {

        Payment payment = new Payment();
        PaymentResponseDto dto = new PaymentResponseDto();

        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(dto);

        List<PaymentResponseDto> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
    }

    @Test
    void getPaymentsForUser_ShouldReturnUserPayments() {

        Payment payment = new Payment();
        PaymentResponseDto dto = new PaymentResponseDto();

        when(paymentRepository.findByRentalUserId(1L)).thenReturn(List.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(dto);

        List<PaymentResponseDto> result = paymentService.getPaymentsForUser(1L);

        assertEquals(1, result.size());
    }
}