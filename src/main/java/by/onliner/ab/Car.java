package by.onliner.ab;

import java.util.List;
import java.util.Map;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */

public class Car {

	public String model;          // наименование модели
	public String year;              // год выпуска
	public String mileage;           // пробег
	public String price;             // цена
	public boolean chaffer;       // торг
	public boolean interchange;   // обмен
	public String description;    // описание

	public boolean conditioning;
	// Опции  //


	public List<Options> optionsList;

	public void setOptionsMap(Map<CarOption, Boolean> optionsMap) {
		conditioning= optionsMap.get(CarOption.CONDITIONING);
		conditioning= optionsMap.get(CarOption.CONDITIONING);
	}


//	conditioning;            // Кондиционер
//	xenon;	                 // Ксенон
//	parktronic;              // Парктроник
//	leatherInterior;	       // Кожаный салон
//	heatedSeats;             // Подогрев сидений
//	navigation;	             // Навигация
//	speakerphone;	           // Громкая связь
//	stabilityControlSystem;  // Система контроля стабилизации
//	alloyWheels;             // Легкосплавные диски


	public enum CarOption {
		CONDITIONING("Кондиционер"),           // Кондиционер
		XENON("Ксенон"),	                 // Ксенон
		PARKTRONIC("Парктрони"),              // Парктрони,
		LEATHER_INTERIOR("Кожаный салон"),     // Кожаный салон
		HEATED_SEATS("Подогрев сидений"),             // Подогрев сидений
		NAVIGATION("Навигация"),	             // Навигация
		SPEAKERPHONE("Громкая связь"),	           // Громкая связь
		STABILITY_CONTROL_SYSTEM("Система контроля стабилизации"),  // Система контроля стабилизации
		ALLOY_WHEELS("Система контроля стабилизации");            // Легкосплавные диски


		private String onlinerName;
		private String abvName;

		CarOption(String onlinerName) {
			this.onlinerName = onlinerName;
		}

		CarOption(String onlinerName, String abvName) {
			this.onlinerName = onlinerName;
		}

		public String getOnlinerName() {
			return onlinerName;
		}
	}


	public Car() {
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isChaffer() {
		return chaffer;
	}

	public void setChaffer(boolean chaffer) {
		this.chaffer = chaffer;
	}

	public boolean isInterchange() {
		return interchange;
	}

	public void setInterchange(boolean interchange) {
		this.interchange = interchange;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Options> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Options> optionsList) {
		this.optionsList = optionsList;
	}
}

