---
name: jetpack-compose-view
description: Use when creating or modifying Android UI views in Jetpack Compose from a screenshot, image reference, mockup, or natural-language description. The skill guides implementation of Compose composables and requires adding a Preview with showBackground = true.
---

# Jetpack Compose View

Uzywaj tego skilla, gdy zadaniem jest stworzenie albo zmiana widoku w Jetpack Compose na podstawie:

- screenshota, makiety lub innego obrazu referencyjnego,
- opisu slowno-funkcjonalnego,
- mieszanki obrazu i opisu.

## Workflow

1. Odczytaj intencje widoku: stan ekranu, hierarchie, teksty, akcje, stany puste/ladowania/bledu oraz ograniczenia z opisu lub obrazu.
2. Sprawdz istniejace wzorce w projekcie: package, motyw, typografie, spacing, komponenty, nazwy plikow i konwencje Preview.
3. Implementuj widok w Compose zgodnie z lokalnym stylem projektu. Preferuj male, czytelne composable tylko wtedy, gdy realnie upraszczaja kod.
4. Dla danych przykladowych uzywaj prostych fake modeli lub parametrow preview blisko widoku, chyba ze projekt ma juz dedykowane preview data.
5. Zawsze dodaj `@Preview(showBackground = true)` dla tworzonego lub istotnie zmienianego widoku.
6. Po zmianach uruchom mozliwie najwezsza sensowna weryfikacje: kompilacje modulu, testy jednostkowe albo przynajmniej statyczne sprawdzenie importow i skladni.

## Compose Rules

- Widok powinien byc sterowany parametrami lub stanem przekazanym z zewnatrz, a nie twardo zaszyta logika biznesowa.
- Teksty, kolory, ksztalty i spacing dopasuj do istniejacego design systemu projektu, jesli taki istnieje.
- Unikaj magicznych rozmiarow, gdy projekt ma tokeny wymiarow albo komponenty wspolne.
- Elementy klikalne powinny przyjmowac lambdy `on...`.
- Dla list uzywaj `LazyColumn`/`LazyRow`, jesli zawartosc moze rosnac.
- Preview ma byc samowystarczalne i kompilowalne.

## Required Preview Pattern

Kazdy nowy lub znaczaco zmieniony widok musi miec preview:

```kotlin
@Preview(showBackground = true)
@Composable
private fun ExampleViewPreview() {
    AppTheme {
        ExampleView(
            // sample args
        )
    }
}
```

Jesli projekt uzywa innej nazwy motywu niz `AppTheme`, zastosuj lokalny motyw projektu.

