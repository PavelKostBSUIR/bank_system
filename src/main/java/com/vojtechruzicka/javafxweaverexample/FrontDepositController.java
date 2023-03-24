package com.vojtechruzicka.javafxweaverexample;

import com.vojtechruzicka.javafxweaverexample.backController.AccountController;
import com.vojtechruzicka.javafxweaverexample.backController.ClientController;
import com.vojtechruzicka.javafxweaverexample.backController.ContractController;
import com.vojtechruzicka.javafxweaverexample.backController.EndBankDayController;
import com.vojtechruzicka.javafxweaverexample.entity.domain.*;
import com.vojtechruzicka.javafxweaverexample.repo.AccountPlanRepository;
import com.vojtechruzicka.javafxweaverexample.repo.DepositRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
@FxmlView("deposit-stage.fxml")
public class FrontDepositController {
    public Label cur;
    public ComboBox<String> contracts;
    private Stage stage;
    private final FxWeaver fxWeaver;
    private final AccountController accountController;
    private final AccountPlanRepository accountPlanRepository;
    private final ClientController clientController;
    private final DepositRepository depositRepository;
    private final EndBankDayController endBankDayController;
    private final ContractController contractController;

    public void setCuurentDate(Date cuurentDate) {
        this.cuurentDate = cuurentDate;
        cur.setText(cuurentDate.toString());
    }

    private Date cuurentDate;
    public TextArea error;
    public TextField sum;
    public TextField startDate;
    public TextField endDate;
    public Label term;
    public CheckBox revocable;
    public Label minSum;
    public Label maxSum;
    public Label percent;
    public Label currencyType;
    public ComboBox<String> deposits;
    public ComboBox<String> clients;
    public TableView<Account> accounts;
    public TableView<AccountPlan> accountPlans;

    @Autowired
    public FrontDepositController(FxWeaver fxWeaver, AccountController accountController, AccountPlanRepository accountPlanRepository, ClientController clientController, DepositRepository depositRepository, EndBankDayController endBankDayController, ContractController contractController) {
        this.fxWeaver = fxWeaver;
        this.accountController = accountController;
        this.accountPlanRepository = accountPlanRepository;
        this.clientController = clientController;
        this.depositRepository = depositRepository;
        this.endBankDayController = endBankDayController;
        this.contractController = contractController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

        TableColumn<Account, String> accountNumberColumn = new TableColumn<Account, String>("AccountNumber");
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("accountNumber"));
        accounts.getColumns().add(accountNumberColumn);

        // столбец для вывода возраста
        TableColumn<Account, String> planColumn = new TableColumn<Account, String>("Plan");
        planColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAccountPlan().getId())));
        accounts.getColumns().add(planColumn);

        TableColumn<Account, String> planNameColumn = new TableColumn<Account, String>("PlanName");
        planNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccountPlan().getAccountName()));
        accounts.getColumns().add(planNameColumn);

        TableColumn<Account, String> clientColumn = new TableColumn<Account, String>("Client");
        clientColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue().getClient();
            if (client != null) {
                return new SimpleStringProperty(client.getId() + " " + client.getSurname() + " " + client.getName() + " " + client.getSecondName());
            } else {
                return new SimpleStringProperty("nulls");
            }

        });
        accounts.getColumns().add(clientColumn);

        TableColumn<Account, String> debetColumn = new TableColumn<Account, String>("Debet");
        debetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDebet())));
        accounts.getColumns().add(debetColumn);

        TableColumn<Account, String> creditColumn = new TableColumn<Account, String>("Credit");
        creditColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCredit())));
        accounts.getColumns().add(creditColumn);

        TableColumn<Account, String> saldoColumn = new TableColumn<Account, String>("Saldo");
        saldoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSaldo())));
        accounts.getColumns().add(saldoColumn);

        TableColumn<AccountPlan, String> accountPlanIdColumn = new TableColumn<AccountPlan, String>("Id");
        accountPlanIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        accountPlans.getColumns().add(accountPlanIdColumn);

        TableColumn<AccountPlan, String> accountPlanNameColumn = new TableColumn<AccountPlan, String>("Name");
        accountPlanNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAccountName())));
        accountPlans.getColumns().add(accountPlanNameColumn);

        TableColumn<AccountPlan, String> accountPlanCodeColumn = new TableColumn<AccountPlan, String>("Code");
        accountPlanCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAccountCode())));
        accountPlans.getColumns().add(accountPlanCodeColumn);

        TableColumn<AccountPlan, String> accountPlanIsActiveColumn = new TableColumn<AccountPlan, String>("IsActive");
        accountPlanIsActiveColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIsActive())));
        accountPlans.getColumns().add(accountPlanIsActiveColumn);

        deposits.setOnAction(actionEvent1 -> {
            if (deposits.isShowing()) {
                Deposit deposit = depositRepository.findById(Long.parseLong(deposits.getValue().split(" ")[0])).get();
                bindDepositToFields(deposit);
            }
        });
        deposits.setOnShowing(actionEvent1 -> refresh());
        clients.setOnShowing(actionEvent1 -> refresh());


        refresh();
    }

    public void addContract() {
        error.setText("");
        Contract contract = bindFieldsToContract();
        if (contract != null) {
            accountController.fill(1L, contract.getSum());
            contractController.post(contract);
            refresh();
        } else {
            error.setText("Some fields are wrong.");
        }
    }

    public void endBankDay() {
        cuurentDate = endBankDayController.endBankDay(cuurentDate);
        cur.setText(cuurentDate.toString());
        refresh();

    }

    private void refresh() {

        ObservableList<String> fxClients = FXCollections.observableArrayList(clientController.getAll().stream().map((Client client) -> client.getId() + " " + client.getSurname() + " " + client.getName() + " " + client.getSecondName()).collect(Collectors.toList()));
        clients.setItems(fxClients);

        ObservableList<String> fxDeposits = FXCollections.observableArrayList(depositRepository.findAll().stream().map((Deposit deposit) -> deposit.getId() + " " + deposit.getName()).collect(Collectors.toList()));
        deposits.setItems(fxDeposits);

        ObservableList<Account> fxAccounts = FXCollections.observableArrayList(accountController.getAll());
        accounts.setItems(fxAccounts);

        ObservableList<AccountPlan> fxAccountPlans = FXCollections.observableArrayList(accountPlanRepository.findAll());
        accountPlans.setItems(fxAccountPlans);
        ObservableList<String> fxContracts = FXCollections.observableArrayList(contractController.getAll().stream().map((Contract contract) -> String.valueOf(contract.getContractNumber())).collect(Collectors.toList()));
        contracts.setItems(fxContracts);
    }

    private void bindDepositToFields(Deposit deposit) {
        term.setText(String.valueOf(deposit.getTerm()));
        revocable.setSelected(deposit.getRevocable());
        minSum.setText(String.valueOf(deposit.getMinSum()));
        maxSum.setText(String.valueOf(deposit.getMaxSum()));
        percent.setText(String.valueOf(deposit.getPercent()));
        currencyType.setText(deposit.getCurrencyType().getName());
    }

    private Contract bindFieldsToContract() {
        error.setText("");
        if (isFieldValid(startDate.getText(), "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$") && isFieldValid(endDate.getText(), "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$") && isComboBoxesValid()) {
            Double sumTemp = Double.valueOf(sum.getText());
            if (startDate.getText().equals(cuurentDate.toString())) {
                if (sumTemp <= Double.parseDouble(maxSum.getText()) && sumTemp >= Double.parseDouble(minSum.getText())) {
                    Contract contract = new Contract();
                    contract.setStartDate(Date.valueOf(startDate.getText()));
                    contract.setEndDate(Date.valueOf(endDate.getText()));
                    contract.setClient(clientController.get(Long.parseLong(clients.getValue().split(" ")[0])));
                    contract.setSum(Double.valueOf(sum.getText()));
                    contract.setDeposit(depositRepository.findById(Long.parseLong(deposits.getValue().split(" ")[0])).get());
                    return contract;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public void revoke() {
        if (contracts.getValue() == null) {
            error.setText("choose contract");
        }
        if (!contractController.revoke(Long.valueOf(contracts.getValue()))) {
            error.setText("Cant revoke");
        }

    }

    private Boolean isFieldValid(String field, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    private Boolean isComboBoxesValid() {
        return !(clients.getValue() == null || deposits.getValue() == null);
    }

    public void openClient() {
        Parent root = fxWeaver.loadView(FrontClientController.class);
        fxWeaver.getBean(FrontClientController.class).setStage(stage);
        fxWeaver.getBean(FrontClientController.class).setCuurentDate(cuurentDate);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Date incrementDate(Date currentDate, int days) {
        String dt = currentDate.toString();  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        return Date.valueOf(dt);
    }

    public void calculateEndDate(ActionEvent inputMethodEvent) {
        error.setText(String.valueOf(isFieldValid(startDate.getText(), "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")));
        if (isFieldValid(startDate.getText(), "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
            endDate.setText(incrementDate(Date.valueOf(startDate.getText()), Integer.parseInt(term.getText()) * 30).toString());
    }
}
