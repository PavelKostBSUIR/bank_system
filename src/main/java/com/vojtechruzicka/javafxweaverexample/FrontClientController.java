package com.vojtechruzicka.javafxweaverexample;

import com.vojtechruzicka.javafxweaverexample.backController.ClientController;
import com.vojtechruzicka.javafxweaverexample.entity.domain.*;
import com.vojtechruzicka.javafxweaverexample.mapper.ClientMapper;
import com.vojtechruzicka.javafxweaverexample.repo.*;
import com.vojtechruzicka.javafxweaverexample.service.AccountService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@FxmlView("main-stage.fxml")
@Getter
public class FrontClientController {
    private Date cuurentDate;

    public void setCuurentDate(Date cuurentDate) {
        this.cuurentDate = cuurentDate;
    }

    private final FxWeaver fxWeaver;
    private final ClientController clientController;
    private final CityRepository cityRepository;
    private final CitizenshipRepository citizenshipRepository;
    private final DisabilityRepository disabilityRepository;
    private final MaritalStatusRepository maritalStatusRepository;
    private final ClientMapper clientMapper;
    private final AccountPlanRepository accountPlanRepository;
    private final CurrencyTypeRepository currencyTypeRepository;
    private final DepositRepository depositRepository;
    private final AccountService accountService;
    private Stage stage;
    private Long clientId;
    @FXML
    private TextField surname;
    @FXML
    private TextField name;
    @FXML
    private TextField secondName;
    @FXML
    private TextField date;
    @FXML
    private TextField passportSeries;
    @FXML
    private TextField passportNumber;
    @FXML
    private TextField issuer;
    @FXML
    private TextField issueDate;
    @FXML
    private TextField passportId;
    @FXML
    private TextField birthplace;
    @FXML
    private ComboBox<String> residentialCity;
    @FXML
    private TextField actualResidentialAddress;
    @FXML
    private TextField homePhoneNumber;
    @FXML
    private TextField mobilePhoneNumber;
    @FXML
    private TextField email;
    @FXML
    private TextField workPlace;
    @FXML
    private TextField post;
    @FXML
    private TextField residentialAddress;
    @FXML
    private ComboBox<String> maritalStatus;
    @FXML
    private ComboBox<String> citizenship;
    @FXML
    private ComboBox<String> disability;
    @FXML
    private ComboBox<String> clients;
    @FXML
    private CheckBox retiree;
    @FXML
    private TextField income;
    @FXML
    private TextArea error;

    @Autowired
    public FrontClientController(FxWeaver fxWeaver, ClientController clientController, CityRepository cityRepository, CitizenshipRepository citizenshipRepository, DisabilityRepository disabilityRepository, MaritalStatusRepository maritalStatusRepository, ClientMapper clientMapper, AccountPlanRepository accountPlanRepository, CurrencyTypeRepository currencyTypeRepository, DepositRepository depositRepository, AccountService accountService) {
        this.fxWeaver = fxWeaver;
        this.clientController = clientController;
        this.cityRepository = cityRepository;
        this.citizenshipRepository = citizenshipRepository;
        this.disabilityRepository = disabilityRepository;
        this.maritalStatusRepository = maritalStatusRepository;
        this.clientMapper = clientMapper;
        this.accountPlanRepository = accountPlanRepository;
        this.currencyTypeRepository = currencyTypeRepository;
        this.depositRepository = depositRepository;
        this.accountService = accountService;
    }


    @FXML
    public void initialize() {
        initializeApp();


        clients.setOnAction(actionEvent1 -> {
            if (clients.isShowing()) {
                Client client = clientController.get(Long.parseLong(clients.getValue().split(" ")[0]));
                clientId = client.getId();
                bindClientToFields(client);
            }
        });
        clients.setOnShowing(actionEvent1 -> refresh());
        residentialCity.setItems(FXCollections.observableArrayList(cityRepository.findAll().stream().map(City::getName).collect(Collectors.toList())));
        citizenship.setItems(FXCollections.observableArrayList(citizenshipRepository.findAll().stream().map(Citizenship::getName).collect(Collectors.toList())));
        disability.setItems(FXCollections.observableArrayList(disabilityRepository.findAll().stream().map(Disability::getName).collect(Collectors.toList())));
        maritalStatus.setItems(FXCollections.observableArrayList(maritalStatusRepository.findAll().stream().map(MaritalStatus::getName).collect(Collectors.toList())));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        error.setText("");
        if (clientId != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Accept deleting");
            alert.setHeaderText("Do you really want to delete this client?");
            alert.setContentText("Press OK to delete and Cancel to exit");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null || option.get() == ButtonType.CANCEL) {
            } else if (option.get() == ButtonType.OK) {
                clientController.delete(clientId);
                clientId = null;
            }

        } else {
            error.setText("Choose client");
        }
    }

    @FXML
    public void edit(ActionEvent actionEvent) {
        error.setText("");
        if (validateFields()) {
            try {

                if (clientId != null) {
                    clientController.put(clientId, clientMapper.ClientToUpdateClientDto(bindFieldsToClient()));
                } else {
                    error.setText("Choose client");
                }
            } catch (IllegalArgumentException e) {
                error.setText("Something wrong with dates or income");
            }
        } else {
            error.setText("Some fields have errors");
        }
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        error.setText("");
        if (validateFields()) {
            try {

                clientController.post(bindFieldsToClient());
            } catch (IllegalArgumentException e) {
                error.setText("Something wrong with dates or income");
            }
        } else {
            error.setText("Some fields have errors");
        }
    }


    private void refresh() {
        //error.setText("");
        //clientController.post(new Client("Костюкевич", "Павел", "Юрьевич"));
        // clientController.post(new Client("Костюкович", "Павел", "Юрьевич"));
        List<Client> clientList = clientController.getAll();
        ObservableList<String> fxClients = FXCollections.observableArrayList(clientList.stream().map((Client client) -> client.getId() + " " + client.getSurname() + " " + client.getName() + " " + client.getSecondName()).collect(Collectors.toList()));
        clients.setItems(fxClients);
    }

    private void bindClientToFields(Client client) {

        surname.setText(client.getSurname());
        name.setText(client.getName());
        secondName.setText(client.getSecondName());
        date.setText(client.getDate().toString());
        passportSeries.setText(client.getPassportSeries());
        passportNumber.setText(client.getPassportNumber());
        issuer.setText(client.getIssuer());
        issueDate.setText(client.getIssueDate().toString());
        passportId.setText(client.getPassportId());
        birthplace.setText(client.getBirthplace());
        residentialCity.setValue(client.getResidentialCity().getName());
        actualResidentialAddress.setText(client.getActualResidentialAddress());
        homePhoneNumber.setText(client.getHomePhoneNumber());
        mobilePhoneNumber.setText(client.getMobilePhoneNumber());
        email.setText(client.getEmail());
        workPlace.setText(client.getWorkPlace());
        post.setText(client.getPost());
        residentialAddress.setText(client.getResidentialAddress());
        maritalStatus.setValue(client.getMaritalStatus().getName());
        citizenship.setValue(client.getCitizenship().getName());
        disability.setValue(client.getDisability().getName());
        retiree.setSelected(client.getRetiree());
        income.setText(String.valueOf(client.getIncome()));
    }

    private Client bindFieldsToClient() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Client client = new Client();
        client.setSurname(surname.getText());
        client.setName(name.getText());
        client.setSecondName(secondName.getText());
        client.setIssueDate(Date.valueOf(issueDate.getText()));
        client.setDate(Date.valueOf(date.getText()));
        if (!(income.getText() == null || income.getText().length() == 0))
            client.setIncome(Double.valueOf(income.getText()));
        client.setPassportSeries(passportSeries.getText());
        client.setPassportNumber(passportNumber.getText());
        client.setIssuer(issuer.getText());
        client.setSurname(surname.getText());
        client.setPassportId(passportId.getText());
        client.setBirthplace(birthplace.getText());
        client.setResidentialCity(new City(residentialCity.getValue()));
        client.setActualResidentialAddress(actualResidentialAddress.getText());
        client.setHomePhoneNumber(homePhoneNumber.getText());
        client.setMobilePhoneNumber(mobilePhoneNumber.getText());
        client.setEmail(email.getText());
        client.setWorkPlace(workPlace.getText());
        client.setPost(post.getText());
        client.setResidentialAddress(residentialAddress.getText());
        client.setMaritalStatus(new MaritalStatus(maritalStatus.getValue()));
        client.setCitizenship(new Citizenship(citizenship.getValue()));
        client.setDisability(new Disability(disability.getValue()));
        client.setRetiree(retiree.isSelected());
        return client;
    }

    private Boolean isComboBoxesValid() {
        return !(maritalStatus.getValue() == null || citizenship.getValue() == null || disability.getValue() == null || residentialCity.getValue() == null);
    }

    private Boolean isNullableFieldValid(String field, String regex) {
        if (field == null || field.length() == 0) return true;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    public Boolean validateFields() {
        return isNullableFieldValid(homePhoneNumber.getText(), "^[\\d]{7}$") && isNullableFieldValid(mobilePhoneNumber.getText(), "^((8|\\+375))?(\\(?\\d{2}\\)?)?[\\d]{7}$") && isNullableFieldValid(email.getText(), "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$") && isComboBoxesValid();

    }

    public void initializeApp() {
        citizenshipRepository.save(new Citizenship("Belarus"));
        citizenshipRepository.save(new Citizenship("Russia"));
        disabilityRepository.save(new Disability("invalid"));
        disabilityRepository.save(new Disability("not invalid"));
        maritalStatusRepository.save(new MaritalStatus("married"));
        maritalStatusRepository.save(new MaritalStatus("single"));
        cityRepository.save(new City("Minsk"));
        cityRepository.save(new City("Vitebsk"));
        cityRepository.save(new City("Molodechno"));
        cityRepository.save(new City("Novogrudok"));
        currencyTypeRepository.save(new CurrencyType("USD"));
        CurrencyType currencyType = currencyTypeRepository.save(new CurrencyType("BYN"));
        depositRepository.save(new Deposit("Revocable deposit", true, 13.0, 2L, 100.0, 100000.0, currencyType));
        depositRepository.save(new Deposit("Unrevocable deposit", false, 20.0, 1L, 100.0, 100000.0, currencyType));
        accountPlanRepository.save(new AccountPlan("Текущий (расчетный) банковский счет физических лиц", 3014L, false));
        accountPlanRepository.save(new AccountPlan("Вклады (депозиты) до востребования физических лиц", 3404L, false));
        accountPlanRepository.save(new AccountPlan("Срочные вклады (депозиты) физических лиц", 3414L, false));
        accountPlanRepository.save(new AccountPlan("Денежные средства в кассе", 1010L, true));
        accountPlanRepository.save(new AccountPlan("Фонд развития", 7327L, false));
        accountService.add(new Account(accountPlanRepository.getOne(4L)));
        Account account = new Account(accountPlanRepository.getOne(5L));
        account.setSaldo(1000000000.0);
        accountService.add(account);
    }

    public void openDeposit() {
        Parent root = fxWeaver.loadView(FrontDepositController.class);
        fxWeaver.getBean(FrontDepositController.class).setStage(stage);
        fxWeaver.getBean(FrontDepositController.class).setCuurentDate(cuurentDate);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
