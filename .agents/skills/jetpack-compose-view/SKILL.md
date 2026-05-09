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
3. Jesli potrzebujesz doprecyzowac dostepne komponenty, zaleznosci, warianty builda, strukture modulu albo sposob uruchamiania weryfikacji, w pierwszej kolejnosci korzystaj z android-cli i lokalnych narzedzi Android/Gradle.
4. Implementuj widok w Compose zgodnie z lokalnym stylem projektu. Granuluj UI na male, czytelne composable, gdy poprawia to czytelnosc, testowalnosc albo pozwala wydzielic elementy z potencjalem reuzywalnosci.
5. Dla danych przykladowych uzywaj prostych fake modeli lub parametrow preview blisko widoku, chyba ze projekt ma juz dedykowane preview data.
6. Zawsze dodaj `@Preview(showBackground = true)` dla tworzonego lub istotnie zmienianego widoku.
7. Po zmianach uruchom mozliwie najwezsza sensowna weryfikacje: kompilacje modulu, testy jednostkowe albo przynajmniej statyczne sprawdzenie importow i skladni.

## Compose Rules

- Widok powinien byc sterowany parametrami lub stanem przekazanym z zewnatrz, a nie twardo zaszyta logika biznesowa.
- Mniejsze fragmenty widoku, szczegolnie te powtarzalne albo mozliwe do uzycia w innych miejscach, powinny miec osobne composable z jasnymi parametrami i lambdami akcji.
- Korzystaj z Theme aplikacji i tokenow dostepnych przez lokalny `MaterialTheme`/design system projektu. Kolory, typografie, ksztalty i spacing bierz z motywu lub istniejacych tokenow; unikaj lokalnie zaszytych wartosci, jesli da sie je wyrazic przez Theme aplikacji.
- Przed dodaniem wlasnych kolorow, typografii, ksztaltow albo wymiarow sprawdz lokalne pliki motywu aplikacji oraz wspolne komponenty UI. Hardcoded wartosci wizualne stosuj tylko jako swiadomy wyjatek, gdy dany detal nie istnieje w motywie i jest potrzebny do wiernego odwzorowania projektu.
- Style tekstu pobieraj z `MaterialTheme.typography`, kolory z `MaterialTheme.colorScheme` albo lokalnych tokenow design systemu, a ksztalty z `MaterialTheme.shapes` lub odpowiednikow projektu. Nie tworz lokalnych palet i typografii wewnatrz widoku, jesli aplikacja ma juz zdefiniowany motyw.
- Preferuj gotowe androidowe komponenty Compose i Material/Material3 dostepne w projekcie przed tworzeniem wlasnych odpowiednikow. Wlasny komponent dodawaj dopiero wtedy, gdy istnieje realna potrzeba wynikajaca z design systemu, zachowania lub braku gotowego komponentu.
- Gdy nie masz pewnosci, czy projekt ma juz odpowiedni komponent albo zaleznosc, najpierw sprawdz lokalny kod i android-cli zamiast zgadywac API.
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
