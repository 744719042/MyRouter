package com.example.routerapi.filter;

public interface Filter {
    Object doFilter(FilterChain chain);
}
