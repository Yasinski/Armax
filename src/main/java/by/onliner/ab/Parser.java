package by.onliner.ab;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.Tag;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {    //TODO: заменить getAllElementsByClass() на  getFirstElementByClass()
	private Source source;

	public void parseIt(String sourceUrlString) throws Exception {
		// в целом все вроде верно. на вскидку. подробнее завтра еще гляну. а так все ок . молодец )
		// все норм завтра остальное обсудим. сеня отдыхай )
		source = getSource(sourceUrlString);
		String line = "-------------------------------------------------------------------------------\n";
		System.out.println(line + line + sourceUrlString);
		if (isActual()) {
			getCost();
			isTorg();
			isObmen();
			getCarModel();
			getCarYear();
			getCarMileage();
			getDescription();
			Map<Car.CarOption, Boolean> optionsList = getOptions();
			System.out.println("---------------------------");
			System.out.println("Опции: ");
			for (Map.Entry<Car.CarOption, Boolean> entry : optionsList.entrySet()) {
				System.out.println(entry.getKey() + " = " + entry.getValue());
//		for (Car.CarOption option : optionsList) {
//			System.out.println(option.getName() + " " + option.isPresence());
			}
			getExplanation();
		} else{
			System.out.println("\nОбъявление удалено");
		}
	}

	private Source getSource(String sourceUrlString) throws Exception {
//		MicrosoftConditionalCommentTagTypes.register(); // не знаю зачем это нужно. завтра расскажешь. или напиши коменты
//		PHPTagTypes.register();           // не знаю зачем это нужно. завтра расскажешь. или напиши коменты
//		PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
//		MasonTagTypes.register();          // не знаю зачем это нужно. завтра расскажешь. или напиши коменты
		return new Source(new URL(sourceUrlString));
	}

	private String getCarModel() {
		String model = null;
		System.out.println("---------------------------");
		System.out.println("Марка авто: ");
		List<Element> strongs = source.getFirstStartTagByClass("autoba-fastchars-ttl").getElement().getChildElements();
		for (Element strong : strongs) {
			model = strong.getContent().toString();
			System.out.println(model);
		}
		return model;
	}

	private String getDescription() {
		String description = null;
		List<Element> elements = source.getAllElementsByClass("autoba-viewoptions");
		System.out.println("---------------------------");
		System.out.println("Описание: ");
		for (Element e : elements) {
			if (!e.isEmpty()) {
				Element el = e.getStartTag().getPreviousTag().getElement();
				description = removeTags(el).toString();
				System.out.println(description);
			}
		}
		return description;
	}

	private Map<Car.CarOption, Boolean> getOptions() {
//		List<Options> options = new ArrayList<Options>();
		List<Element> divs = source.getAllElementsByClass("autoba-viewoptions");
		Map<Car.CarOption, Boolean> optionsMap = new HashMap<Car.CarOption, Boolean>();

		if (!divs.isEmpty()) {
			for (Element div : divs) {
				if (!div.isEmpty()) {
					List<Element> uls = div.getChildElements();
					if (!uls.isEmpty()) {
						for (Element ul : uls) {
							if (!ul.isEmpty()) {
								List<Element> lis = ul.getChildElements();
								if (!lis.isEmpty()) {
									for (Element li : lis) {
										if (!li.isEmpty()) {
											String optionName = li.getContent().toString();
											for (Car.CarOption carOption : Car.CarOption.values()) {
												if (carOption.getOnlinerName().equals(optionName)) {
													String className = li.getAttributeValue("class");
													if (className != null) {
														if (className.equals("none")) {
															optionsMap.put(carOption, false);
														}
													} else {
														optionsMap.put(carOption, true);
													}
												}
											}
//											Options option = new Options();
//											option.setName(li.getContent().toString());
//											String className = li.getAttributeValue("class");
//											if (className != null) {
//												if (className.equals("none")) {
//													option.setPresence(false);
//												}
//											} else {
//												option.setPresence(true);
//											}
//											options.add(option);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return optionsMap;
	}

	private String getCarYear() {
		String year = null;
		System.out.println("---------------------------");
		System.out.println("Год выпуска: ");
		List<Element> strong = source.getFirstStartTagByClass("year").getElement().getChildElements();
		for (Element strng : strong) {
			year = strng.getContent().toString();
			System.out.println(year);
		}
		return year;
	}

	private String getCarMileage() {
		String year = null;
		System.out.println("---------------------------");
		System.out.println("Пробег: ");
		List<Element> strongs = source.getFirstStartTagByClass("dist").getElement().getChildElements();
		for (Element strng : strongs) {
			year = strng.getContent().toString();
			System.out.println(year);
		}
		return year;
	}

	private String getCost() {
		String cost = "цена не указана";
		System.out.println("---------------------------");
		System.out.println("Цена ($): ");
		Tag startTagOfClass = source.getFirstStartTagByClass("cost");
		if (startTagOfClass != null) {
			List<Element> childs = startTagOfClass.getElement().getChildElements();
			if (!childs.isEmpty()) {
				for (Element strong : childs) {
					cost = strong.getContent().toString();
					if (cost != null) {
					}
				}
			}
		}
		System.out.println(cost);
		return cost;
	}

	private boolean isTorg() {
		boolean torg = false;
		System.out.println("---------------------------");
		System.out.println("Торг: ");
		Tag startTagOfClass = source.getFirstStartTagByClass("c-torg");
		if (startTagOfClass != null) {
			torg = true;
		}
		System.out.println(torg);
		return torg;
	}

	private boolean isObmen() {
		boolean obmen = false;
		System.out.println("---------------------------");
		System.out.println("Обмен: ");
		Tag startTagOfClass = source.getFirstStartTagByClass("c-change");
		if (startTagOfClass != null) {
			obmen = true;
		}
		System.out.println(obmen);
		return obmen;
	}


	private OutputDocument removeTags(Element element) {
		OutputDocument outputDocument = new OutputDocument(element);
		outputDocument.remove(element.getStartTag());
		if (!element.getStartTag().isSyntacticalEmptyElementTag()) {
			outputDocument.remove(element.getEndTag());
			for (Element e : element.getChildElements()) {
				if (e != null) {
					outputDocument.remove(e.getStartTag());
					if (!e.getStartTag().isSyntacticalEmptyElementTag()) {
						outputDocument.remove(e.getEndTag());
					}
				}
			}
		}
		return outputDocument;
	}

	private String getExplanation() {
		String explanation = "-";
		Element element = source.getFirstElementByClass("autoba-msglongcont");
		System.out.println("---------------------------");
		System.out.println("Примечание: ");
		if (!element.isEmpty()) {
			explanation = element.getStartTag().getPreviousTag().getElement().getContent().toString();
		}
		System.out.println(explanation);
		return explanation;
	}

	private boolean isActual() {
		Element element = source.getFirstElementByClass("not-found");
		return element == null;
	}

}