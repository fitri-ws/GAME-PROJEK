from collections import Counter
import itertools

# Frekuensi huruf umum bahasa Inggris
english_freq_orders = [
    "ETAOINSHRDLCUMWFGYPBVKJXQZ",  # Standard
    "EARIOTNSLCUDPMHGBFYWKVXZJQ",  # Alternative 1
    "ETAOINSRHDLUCMFYWGPBVKXJQZ",  # Alternative 2
]

def analyze_frequencies(text):
    text = text.upper()
    letters_only = [c for c in text if c.isalpha()]
    freq = Counter(letters_only)
    sorted_freq = [item[0] for item in freq.most_common()]
    return sorted_freq, freq

def decrypt_with_mapping(text, mapping):
    result = ""
    for char in text:
        if char.upper() in mapping:
            subst = mapping[char.upper()]
            result += subst.lower() if char.islower() else subst
        else:
            result += char
    return result

def generate_decryption_attempts(ciphertext, freq_orders):
    sorted_freq, freq_count = analyze_frequencies(ciphertext)
    attempts = []

    print("=== Frekuensi Huruf dalam Ciphertext ===")
    for letter, count in freq_count.items():
        print(f"{letter}: {count}")
    print("\n")

    for idx, order in enumerate(freq_orders):
        mapping = {}
        for i in range(min(len(sorted_freq), len(order))):
            mapping[sorted_freq[i]] = order[i]
        decrypted = decrypt_with_mapping(ciphertext, mapping)
        attempts.append((idx + 1, decrypted, mapping))
    
    return attempts

# Ciphertext contoh
ciphertext = "YMJ VZNHP GWTBS KTC OZRUX TAJW YMJ QFED ITL"

# Lakukan analisis dan tampilkan alternatif hasil
attempts = generate_decryption_attempts(ciphertext, english_freq_orders)

# Tampilkan semua hasil
for idx, text, mapping in attempts:
    print(f"=== Alternatif #{idx} ===")
    print("Mapping digunakan:")
    print(mapping)
    print("\nHasil dekripsi:")
    print(text)
    print("\n")
    

