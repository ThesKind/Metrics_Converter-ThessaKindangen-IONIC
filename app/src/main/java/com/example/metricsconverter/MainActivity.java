package com.example.metricsconverter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner conversionType;
    private Spinner fromUnit;
    private Spinner toUnit;
    private Button convertButton;
    private TextView resultTextView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.inputValue);
        conversionType = findViewById(R.id.conversionType);
        fromUnit = findViewById(R.id.fromUnit);
        toUnit = findViewById(R.id.toUnit);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        titleTextView = findViewById(R.id.titleTextView);

        // Set judul aplikasi
        titleTextView.setText("Metric Converter");

        // Adapter untuk jenis konversi
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionType.setAdapter(typeAdapter);

        // Update unit saat jenis konversi dipilih
        conversionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Tombol konversi
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
    }

    // Update unit spinner berdasarkan jenis konversi yang dipilih
    private void updateUnitSpinners() {
        String selectedType = conversionType.getSelectedItem().toString();
        int unitArrayId = 0;

        switch (selectedType) {
            case "Panjang":
                unitArrayId = R.array.length_units;
                break;
            case "Massa":
                unitArrayId = R.array.mass_units;
                break;
            case "Waktu":
                unitArrayId = R.array.time_units;
                break;
            case "Arus Listrik":
                unitArrayId = R.array.current_units;
                break;
            case "Suhu":
                unitArrayId = R.array.temperature_units;
                break;
            case "Kecepatan":
                unitArrayId = R.array.speed_units;
                break;
            case "Frekuensi":
                unitArrayId = R.array.frequency_units;
                break;
            case "Luas":
                unitArrayId = R.array.area_units;
                break;
            case "Intensitas Cahaya":
                unitArrayId = R.array.luminous_intensity_units;
                break;
            case "Jumlah Zat":
                unitArrayId = R.array.amount_of_substance_units;
                break;
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                unitArrayId, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnit.setAdapter(unitAdapter);
        toUnit.setAdapter(unitAdapter);
    }

    // Metode konversi
    private void convert() {
        String inputValueStr = inputValue.getText().toString();
        if (inputValueStr.isEmpty()) {
            resultTextView.setText("Masukkan nilai terlebih dahulu.");
            return;
        }

        double value = Double.parseDouble(inputValueStr);
        String fromUnitStr = fromUnit.getSelectedItem().toString();
        String toUnitStr = toUnit.getSelectedItem().toString();
        String conversionTypeStr = conversionType.getSelectedItem().toString();
        double result = 0;

        // Logika konversi untuk tiap jenis besaran
        switch (conversionTypeStr) {
            case "Panjang":
                result = convertLength(value, fromUnitStr, toUnitStr);
                break;
            case "Massa":
                result = convertMass(value, fromUnitStr, toUnitStr);
                break;
            case "Waktu":
                result = convertTime(value, fromUnitStr, toUnitStr);
                break;
            case "Suhu":
                result = convertTemperature(value, fromUnitStr, toUnitStr);
                break;
            case "Kecepatan":
                result = convertSpeed(value, fromUnitStr, toUnitStr);
                break;
            case "Frekuensi":
                result = convertFrequency(value, fromUnitStr, toUnitStr);
                break;
            case "Luas":
                result = convertArea(value, fromUnitStr, toUnitStr);
                break;
            // Tambahkan konversi untuk jenis besaran lainnya jika diperlukan
        }

        // Tampilkan hasil dengan format yang jelas
        String resultText = String.format("%.2f %s", result, toUnitStr);
        resultTextView.setText("Hasil: " + resultText);
    }

    // Metode konversi panjang
    private double convertLength(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Length conversion: from " + fromUnit + " to " + toUnit);

        // Konversi dari Meter ke satuan lainnya
        if (fromUnit.equals("Meter")) {
            if (toUnit.equals("Kilometer")) {
                return value / 1000;
            } else if (toUnit.equals("Centimeter")) {
                return value * 100;
            } else if (toUnit.equals("Millimeter")) {
                return value * 1000;
            }
        }
        // Konversi dari Kilometer ke satuan lainnya
        else if (fromUnit.equals("Kilometer")) {
            if (toUnit.equals("Meter")) {
                return value * 1000;
            } else if (toUnit.equals("Centimeter")) {
                return value * 100000;
            } else if (toUnit.equals("Millimeter")) {
                return value * 1000000;
            }
        }
        // Konversi dari Centimeter ke satuan lainnya
        else if (fromUnit.equals("Centimeter")) {
            if (toUnit.equals("Meter")) {
                return value / 100;
            } else if (toUnit.equals("Kilometer")) {
                return value / 100000;
            } else if (toUnit.equals("Millimeter")) {
                return value * 10;
            }
        }
        // Konversi dari Millimeter ke satuan lainnya
        else if (fromUnit.equals("Millimeter")) {
            if (toUnit.equals("Meter")) {
                return value / 1000;
            } else if (toUnit.equals("Kilometer")) {
                return value / 1000000;
            } else if (toUnit.equals("Centimeter")) {
                return value / 10;
            }
        }

        // Default jika tidak ada konversi yang sesuai
        return value;
    }

    // Metode konversi massa
    private double convertMass(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Mass conversion: from " + fromUnit + " to " + toUnit);

        if (fromUnit.equals("Gram") && toUnit.equals("Kilogram")) {
            return value / 1000;
        } else if (fromUnit.equals("Kilogram") && toUnit.equals("Gram")) {
            return value * 1000;
        }
        // Tambahkan kasus konversi massa lainnya jika diperlukan
        return value; // Default jika tidak ada konversi yang sesuai
    }

    // Metode konversi waktu
    private double convertTime(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Time conversion: from " + fromUnit + " to " + toUnit);

        if (fromUnit.equals("Detik") && toUnit.equals("Menit")) {
            return value / 60;
        } else if (fromUnit.equals("Menit") && toUnit.equals("Detik")) {
            return value * 60;
        }
        // Tambahkan kasus konversi waktu lainnya jika diperlukan
        return value; // Default jika tidak ada konversi yang sesuai
    }

    // Metode konversi suhu
    private double convertTemperature(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Temperature conversion: from " + fromUnit + " to " + toUnit);

        if (fromUnit.equals("Celcius")) {
            if (toUnit.equals("Kelvin")) {
                return value + 273.15;
            } else if (toUnit.equals("Fahrenheit")) {
                return (value * 9/5) + 32;
            }
        } else if (fromUnit.equals("Kelvin")) {
            if (toUnit.equals("Celcius")) {
                return value - 273.15;
            } else if (toUnit.equals("Fahrenheit")) {
                return (value - 273.15) * 9/5 + 32;
            }
        } else if (fromUnit.equals("Fahrenheit")) {
            if (toUnit.equals("Celcius")) {
                return (value - 32) * 5/9;
            } else if (toUnit.equals("Kelvin")) {
                return (value - 32) * 5/9 + 273.15;
            }
        }
        // Default jika tidak ada konversi yang sesuai
        return value;
    }

    // Metode konversi kecepatan
    private double convertSpeed(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Speed conversion: from " + fromUnit + " to " + toUnit);

        // Konversi dari meter per detik ke satuan lainnya
        if (fromUnit.equals("Meter per detik")) {
            if (toUnit.equals("Kilometer per jam")) {
                return value * 3.6;
            } else if (toUnit.equals("Mil per jam")) {
                return value * 2.237;
            }
        }
        // Konversi dari kilometer per jam ke satuan lainnya
        else if (fromUnit.equals("Kilometer per jam")) {
            if (toUnit.equals("Meter per detik")) {
                return value / 3.6;
            } else if (toUnit.equals("Mil per jam")) {
                return value * 0.621371;
            }
        }
        // Konversi dari mil per jam ke satuan lainnya
        else if (fromUnit.equals("Mil per jam")) {
            if (toUnit.equals("Meter per detik")) {
                return value * 0.44704;
            } else if (toUnit.equals("Kilometer per jam")) {
                return value / 0.621371;
            }
        }

        // Default jika tidak ada konversi yang sesuai
        return value;
    }

    // Metode konversi frekuensi
    private double convertFrequency(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Frequency conversion: from " + fromUnit + " to " + toUnit);

        // Konversi dari Hertz ke satuan lainnya
        if (fromUnit.equals("Hertz")) {
            if (toUnit.equals("Kilohertz")) {
                return value / 1000;
            } else if (toUnit.equals("Megahertz")) {
                return value / 1_000_000;
            } else if (toUnit.equals("Gigahertz")) {
                return value / 1_000_000_000;
            }
        }
        // Konversi dari Kilohertz ke satuan lainnya
        else if (fromUnit.equals("Kilohertz")) {
            if (toUnit.equals("Hertz")) {
                return value * 1000;
            } else if (toUnit.equals("Megahertz")) {
                return value / 1000;
            } else if (toUnit.equals("Gigahertz")) {
                return value / 1_000_000;
            }
        }
        // Konversi dari Megahertz ke satuan lainnya
        else if (fromUnit.equals("Megahertz")) {
            if (toUnit.equals("Hertz")) {
                return value * 1_000_000;
            } else if (toUnit.equals("Kilohertz")) {
                return value * 1000;
            } else if (toUnit.equals("Gigahertz")) {
                return value / 1000;
            }
        }
        // Konversi dari Gigahertz ke satuan lainnya
        else if (fromUnit.equals("Gigahertz")) {
            if (toUnit.equals("Hertz")) {
                return value * 1_000_000_000;
            } else if (toUnit.equals("Kilohertz")) {
                return value * 1_000_000;
            } else if (toUnit.equals("Megahertz")) {
                return value * 1000;
            }
        }

        // Default jika tidak ada konversi yang sesuai
        return value;
    }

    // Metode konversi luas
    private double convertArea(double value, String fromUnit, String toUnit) {
        Log.d("Conversion", "Area conversion: from " + fromUnit + " to " + toUnit);

        // Konversi dari meter persegi ke satuan lainnya
        if (fromUnit.equals("Meter persegi")) {
            if (toUnit.equals("Hektar")) {
                return value / 10_000;
            } else if (toUnit.equals("Inci")) {
                return value * 1550.0031;
            }
        }
        // Konversi dari hektar ke satuan lainnya
        else if (fromUnit.equals("Hektar")) {
            if (toUnit.equals("Meter persegi")) {
                return value * 10_000;
            } else if (toUnit.equals("Inci")) {
                return value * 15500031;
            }
        }
        // Konversi dari inci ke satuan lainnya
        else if (fromUnit.equals("Inci")) {
            if (toUnit.equals("Meter persegi")) {
                return value / 1550.0031;
            } else if (toUnit.equals("Hektar")) {
                return value / 15500031;
            }
        }

        // Default jika tidak ada konversi yang sesuai
        return value;
    }
}
