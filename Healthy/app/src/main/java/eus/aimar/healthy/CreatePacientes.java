package eus.aimar.healthy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collections;

import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.Model.Paciente;
import eus.aimar.healthy.ViewModel.MedicoViewModel;
import eus.aimar.healthy.ViewModel.PacienteViewModel;
import eus.aimar.healthy.databinding.CreatePacientesBinding;

public class CreatePacientes extends Fragment {
    String name, surname, medicine;
    CreatePacientesBinding binding;
    NavController navController;
    PacienteViewModel pacienteViewModel;
    MedicoViewModel medicoViewModel;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Ibuprofeno", "Paracetamol", "Antibioticos"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = CreatePacientesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        pacienteViewModel = new ViewModelProvider(requireActivity()).get(PacienteViewModel.class);
        medicoViewModel = new ViewModelProvider(requireActivity()).get(MedicoViewModel.class);
        selectedLanguage = new boolean[langArray.length];
        // Get the logged-in Medico from the ViewModel
        Medico loggedInMedico = medicoViewModel.getLoggedInMedico();

        if (loggedInMedico != null) {
            binding.textViewMedicamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Initialize alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

                    // set title
                    builder.setTitle("Select Medicine");

                    // set dialog non-cancelable
                    builder.setCancelable(false);

                    builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            // check condition
                            if (b) {
                                // when checkbox is selected
                                // Add position in lang list
                                langList.add(i);
                                // Sort the array list
                                Collections.sort(langList);
                            } else {
                                // when checkbox is unselected
                                // Remove position from langList
                                langList.remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Initialize string builder
                            StringBuilder stringBuilder = new StringBuilder();
                            // use a for loop
                            for (int j = 0; j < langList.size(); j++) {
                                // concat array value
                                stringBuilder.append(langArray[langList.get(j)]);
                                // check condition
                                if (j != langList.size() - 1) {
                                    // When j value is not equal
                                    // to lang list size - 1
                                    // add a comma
                                    stringBuilder.append(", ");
                                }
                            }
                            // set text on textView
                            binding.textViewMedicamento.setText(stringBuilder.toString());
                        }
                    });


                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // dismiss the dialog
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // use a for loop
                            for (int j = 0; j < selectedLanguage.length; j++) {
                                // remove all selection
                                selectedLanguage[j] = false;
                                // clear the language list
                                langList.clear();
                                // clear the text view value
                                binding.textViewMedicamento.setText("");
                            }
                        }
                    });

                    // show the dialog
                    builder.show();
                }
            });
//            binding.addMedicamentoButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder addLanguageDialog = new AlertDialog.Builder(requireContext());
//                    addLanguageDialog.setTitle("Add New Language");
//
//                    final EditText newLanguageEditText = new EditText(requireContext());
//                    newLanguageEditText.setInputType(InputType.TYPE_CLASS_TEXT);
//                    addLanguageDialog.setView(newLanguageEditText);
//
//                    addLanguageDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String newLanguage = newLanguageEditText.getText().toString().trim();
//                            if (!newLanguage.isEmpty()) {
//                                langArray = Arrays.copyOf(langArray, langArray.length + 1);
//                                langArray[langArray.length - 1] = newLanguage;
//
//                                // Update the selectedLanguage array
//                                selectedLanguage = Arrays.copyOf(selectedLanguage, selectedLanguage.length + 1);
//                                selectedLanguage[selectedLanguage.length - 1] = false;
//
//                                Toast.makeText(getContext(), "New language added: " + newLanguage, Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), "Language name cannot be empty", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//                    addLanguageDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    addLanguageDialog.show();
//                }
//            });
            binding.createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        name = binding.createName.getText().toString();
                        surname = binding.createSurname.getText().toString();
                        medicine = binding.textViewMedicamento.getText().toString();
                if(name.isEmpty()||surname.isEmpty()||medicine.isEmpty()){
                    // Set the text with the ID of the logged-in Medico
                    Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
                else{
                    int id = loggedInMedico.getId();
                    Paciente paciente = new Paciente(id, name, surname, medicine);
                    navController.navigate(R.id.action_createPacientes_to_show);
                    // Add the patient to the ViewModel
                    pacienteViewModel.add(paciente);
                    String patientInfo = "Name: " + paciente.getNombre() + ", Surname: " + paciente.getApellidos();
                    Toast.makeText(getContext(), patientInfo, Toast.LENGTH_SHORT).show();

                }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
