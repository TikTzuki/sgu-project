CEASAR:

```
    key =k 
    encode:
    y = y + k mod 26
    decode:
    y = y - k mod 26
```

SubtitutionCipher:

```
    foreach char a equal a'
    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
    x, n, y, a, h, p, o, g, z, q, w, b, t, s, f, l, r, c, v, m, u, e, k, j, d, i
```

APPHIN:

```
    key = (k1, k2)
    encode:
    y = y * k1 + k2 mod 26
    decode:
    y = reverse(26, k1) * (y-k2) mod 26
```