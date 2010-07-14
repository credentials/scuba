/*
 * $Id: $
 */

package net.sourceforge.scuba.data;

import java.io.Serializable;

/**
 * ISO 3166 country codes.
 * Table based on Wikipedia information.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class ISOCountry extends Country
{
	private static final long serialVersionUID = 7220597933847617859L;

	public static final ISOCountry
	AD = new ISOCountry(0x020, "AD", "AND", "Andorra"),
	AE = new ISOCountry(0x784, "AE", "ARE", "United Arab Emirates"),
	AF = new ISOCountry(0x004, "AF", "AFG", "Afghanistan"),
	AG = new ISOCountry(0x028, "AG", "ATG", "Antigua and Barbuda"),
	AI = new ISOCountry(0x660, "AI", "AIA", "Anguilla"),
	AL = new ISOCountry(0x008, "AL", "ALB", "Albania"),
	AM = new ISOCountry(0x051, "AM", "ARM", "Armenia"),
	AN = new ISOCountry(0x530, "AN", "ANT", "Netherlands Antilles"),
	AO = new ISOCountry(0x024, "AO", "AGO", "Angola"),
	AQ = new ISOCountry(0x010, "AQ", "ATA", "Antarctica"),
	AR = new ISOCountry(0x032, "AR", "ARG", "Argentina"),
	AS = new ISOCountry(0x016, "AS", "ASM", "American Samoa"),
	AT = new ISOCountry(0x040, "AT", "AUT", "Austria"),
	AU = new ISOCountry(0x036, "AU", "AUS", "Australia"),
	AW = new ISOCountry(0x533, "AW", "ABW", "Aruba"),
	AX = new ISOCountry(0x248, "AX", "ALA", "Aland Islands"),
	AZ = new ISOCountry(0x031, "AZ", "AZE", "Azerbaijan"),
	BA = new ISOCountry(0x070, "BA", "BIH", "Bosnia and Herzegovina"),
	BB = new ISOCountry(0x052, "BB", "BRB", "Barbados"),
	BD = new ISOCountry(0x050, "BD", "BGD", "Bangladesh"),
	BE = new ISOCountry(0x056, "BE", "BEL", "Belgium"),
	BF = new ISOCountry(0x854, "BF", "BFA", "Burkina Faso"),
	BG = new ISOCountry(0x100, "BG", "BGR", "Bulgaria"),
	BH = new ISOCountry(0x048, "BH", "BHR", "Bahrain"),
	BI = new ISOCountry(0x108, "BI", "BDI", "Burundi"),
	BJ = new ISOCountry(0x204, "BJ", "BEN", "Benin"),
	BL = new ISOCountry(0x652, "BL", "BLM", "Saint Barthlemy"),
	BM = new ISOCountry(0x060, "BM", "BMU", "Bermuda"),
	BN = new ISOCountry(0x096, "BN", "BRN", "Brunei Darussalam"),
	BO = new ISOCountry(0x068, "BO", "BOL", "Bolivia"),
	BR = new ISOCountry(0x076, "BR", "BRA", "Brazil"),
	BS = new ISOCountry(0x044, "BS", "BHS", "Bahamas"),
	BT = new ISOCountry(0x064, "BT", "BTN", "Bhutan"),
	BV = new ISOCountry(0x074, "BV", "BVT", "Bouvet Island"),
	BW = new ISOCountry(0x072, "BW", "BWA", "Botswana"),
	BY = new ISOCountry(0x112, "BY", "BLR", "Belarus"),
	BZ = new ISOCountry(0x084, "BZ", "BLZ", "Belize"),
	CA = new ISOCountry(0x124, "CA", "CAN", "Canada"),
	CC = new ISOCountry(0x166, "CC", "CCK", "Cocos (Keeling) Islands"),
	CD = new ISOCountry(0x180, "CD", "COD", "Congo the Democratic Republic of the"),
	CF = new ISOCountry(0x140, "CF", "CAF", "Central African Republic"),
	CG = new ISOCountry(0x178, "CG", "COG", "Congo"),
	CH = new ISOCountry(0x756, "CH", "CHE", "Switzerland"),
	CI = new ISOCountry(0x384, "CI", "CIV", "Cote d'Ivoire"),
	CK = new ISOCountry(0x184, "CK", "COK", "Cook Islands"),
	CL = new ISOCountry(0x152, "CL", "CHL", "Chile"),
	CM = new ISOCountry(0x120, "CM", "CMR", "Cameroon"),
	CN = new ISOCountry(0x156, "CN", "CHN", "China"),
	CO = new ISOCountry(0x170, "CO", "COL", "Colombia"),
	CR = new ISOCountry(0x188, "CR", "CRI", "Costa Rica"),
	CU = new ISOCountry(0x192, "CU", "CUB", "Cuba"),
	CV = new ISOCountry(0x132, "CV", "CPV", "Cape Verde"),
	CX = new ISOCountry(0x162, "CX", "CXR", "Christmas Island"),
	CY = new ISOCountry(0x196, "CY", "CYP", "Cyprus"),
	CZ = new ISOCountry(0x203, "CZ", "CZE", "Czech Republic"),
	DE = new ISOCountry(0x276, "DE", "DEU", "Germany"),
	DJ = new ISOCountry(0x262, "DJ", "DJI", "Djibouti"),
	DK = new ISOCountry(0x208, "DK", "DNK", "Denmark"),
	DM = new ISOCountry(0x212, "DM", "DMA", "Dominica"),
	DO = new ISOCountry(0x214, "DO", "DOM", "Dominican Republic"),
	DZ = new ISOCountry(0x012, "DZ", "DZA", "Algeria"),
	EC = new ISOCountry(0x218, "EC", "ECU", "Ecuador"),
	EE = new ISOCountry(0x233, "EE", "EST", "Estonia"),
	EG = new ISOCountry(0x818, "EG", "EGY", "Egypt"),
	EH = new ISOCountry(0x732, "EH", "ESH", "Western Sahara"),
	ER = new ISOCountry(0x232, "ER", "ERI", "Eritrea"),
	ES = new ISOCountry(0x724, "ES", "ESP", "Spain"),
	ET = new ISOCountry(0x231, "ET", "ETH", "Ethiopia"),
	FI = new ISOCountry(0x246, "FI", "FIN", "Finland"),
	FJ = new ISOCountry(0x242, "FJ", "FJI", "Fiji"),
	FK = new ISOCountry(0x238, "FK", "FLK", "Falkland Islands (Malvinas)"),
	FM = new ISOCountry(0x583, "FM", "FSM", "Micronesia Federated States of"),
	FO = new ISOCountry(0x234, "FO", "FRO", "Faroe Islands"),
	FR = new ISOCountry(0x250, "FR", "FRA", "France"),
	GA = new ISOCountry(0x266, "GA", "GAB", "Gabon"),
	GB = new ISOCountry(0x826, "GB", "GBR", "United Kingdom"),
	GD = new ISOCountry(0x308, "GD", "GRD", "Grenada"),
	GE = new ISOCountry(0x268, "GE", "GEO", "Georgia"),
	GF = new ISOCountry(0x254, "GF", "GUF", "French Guiana"),
	GG = new ISOCountry(0x831, "GG", "GGY", "Guernsey"),
	GH = new ISOCountry(0x288, "GH", "GHA", "Ghana"),
	GI = new ISOCountry(0x292, "GI", "GIB", "Gibraltar"),
	GL = new ISOCountry(0x304, "GL", "GRL", "Greenland"),
	GM = new ISOCountry(0x270, "GM", "GMB", "Gambia"),
	GN = new ISOCountry(0x324, "GN", "GIN", "Guinea"),
	GP = new ISOCountry(0x312, "GP", "GLP", "Guadeloupe"),
	GQ = new ISOCountry(0x226, "GQ", "GNQ", "Equatorial Guinea"),
	GR = new ISOCountry(0x300, "GR", "GRC", "Greece"),
	GS = new ISOCountry(0x239, "GS", "SGS", "South Georgia and the South Sandwich Islands"),
	GT = new ISOCountry(0x320, "GT", "GTM", "Guatemala"),
	GU = new ISOCountry(0x316, "GU", "GUM", "Guam"),
	GW = new ISOCountry(0x624, "GW", "GNB", "Guinea-Bissau"),
	GY = new ISOCountry(0x328, "GY", "GUY", "Guyana"),
	HK = new ISOCountry(0x344, "HK", "HKG", "Hong Kong"),
	HM = new ISOCountry(0x334, "HM", "HMD", "Heard Island and McDonald Islands"),
	HN = new ISOCountry(0x340, "HN", "HND", "Honduras"),
	HR = new ISOCountry(0x191, "HR", "HRV", "Croatia"),
	HT = new ISOCountry(0x332, "HT", "HTI", "Haiti"),
	HU = new ISOCountry(0x348, "HU", "HUN", "Hungary"),
	ID = new ISOCountry(0x360, "ID", "IDN", "Indonesia"),
	IE = new ISOCountry(0x372, "IE", "IRL", "Ireland"),
	IL = new ISOCountry(0x376, "IL", "ISR", "Israel"),
	IM = new ISOCountry(0x833, "IM", "IMN", "Isle of Man"),
	IN = new ISOCountry(0x356, "IN", "IND", "India"),
	IO = new ISOCountry(0x086, "IO", "IOT", "British Indian Ocean Territory"),
	IQ = new ISOCountry(0x368, "IQ", "IRQ", "Iraq"),
	IR = new ISOCountry(0x364, "IR", "IRN", "Iran Islamic Republic of"),
	IS = new ISOCountry(0x352, "IS", "ISL", "Iceland"),
	IT = new ISOCountry(0x380, "IT", "ITA", "Italy"),
	JE = new ISOCountry(0x832, "JE", "JEY", "Jersey"),
	JM = new ISOCountry(0x388, "JM", "JAM", "Jamaica"),
	JO = new ISOCountry(0x400, "JO", "JOR", "Jordan"),
	JP = new ISOCountry(0x392, "JP", "JPN", "Japan"),
	KE = new ISOCountry(0x404, "KE", "KEN", "Kenya"),
	KG = new ISOCountry(0x417, "KG", "KGZ", "Kyrgyzstan"),
	KH = new ISOCountry(0x116, "KH", "KHM", "Cambodia"),
	KI = new ISOCountry(0x296, "KI", "KIR", "Kiribati"),
	KM = new ISOCountry(0x174, "KM", "COM", "Comoros"),
	KN = new ISOCountry(0x659, "KN", "KNA", "Saint Kitts and Nevis"),
	KP = new ISOCountry(0x408, "KP", "PRK", "Korea Democratic People's Republic of"),
	KR = new ISOCountry(0x410, "KR", "KOR", "Korea Republic of"),
	KW = new ISOCountry(0x414, "KW", "KWT", "Kuwait"),
	KY = new ISOCountry(0x136, "KY", "CYM", "Cayman Islands"),
	KZ = new ISOCountry(0x398, "KZ", "KAZ", "Kazakhstan"),
	LA = new ISOCountry(0x418, "LA", "LAO", "Lao People's Democratic Republic"),
	LB = new ISOCountry(0x422, "LB", "LBN", "Lebanon"),
	LC = new ISOCountry(0x662, "LC", "LCA", "Saint Lucia"),
	LI = new ISOCountry(0x438, "LI", "LIE", "Liechtenstein"),
	LK = new ISOCountry(0x144, "LK", "LKA", "Sri Lanka"),
	LR = new ISOCountry(0x430, "LR", "LBR", "Liberia"),
	LS = new ISOCountry(0x426, "LS", "LSO", "Lesotho"),
	LT = new ISOCountry(0x440, "LT", "LTU", "Lithuania"),
	LU = new ISOCountry(0x442, "LU", "LUX", "Luxembourg"),
	LV = new ISOCountry(0x428, "LV", "LVA", "Latvia"),
	LY = new ISOCountry(0x434, "LY", "LBY", "Libyan Arab Jamahiriya"),
	MA = new ISOCountry(0x504, "MA", "MAR", "Morocco"),
	MC = new ISOCountry(0x492, "MC", "MCO", "Monaco"),
	MD = new ISOCountry(0x498, "MD", "MDA", "Moldova"),
	ME = new ISOCountry(0x499, "ME", "MNE", "Montenegro"),
	MF = new ISOCountry(0x663, "MF", "MAF", "Saint Martin (French part)"),
	MG = new ISOCountry(0x450, "MG", "MDG", "Madagascar"),
	MH = new ISOCountry(0x584, "MH", "MHL", "Marshall Islands"),
	MK = new ISOCountry(0x807, "MK", "MKD", "Macedonia the former Yugoslav Republic of"),
	ML = new ISOCountry(0x466, "ML", "MLI", "Mali"),
	MM = new ISOCountry(0x104, "MM", "MMR", "Myanmar"),
	MN = new ISOCountry(0x496, "MN", "MNG", "Mongolia"),
	MO = new ISOCountry(0x446, "MO", "MAC", "Macao"),
	MP = new ISOCountry(0x580, "MP", "MNP", "Northern Mariana Islands"),
	MQ = new ISOCountry(0x474, "MQ", "MTQ", "Martinique"),
	MR = new ISOCountry(0x478, "MR", "MRT", "Mauritania"),
	MS = new ISOCountry(0x500, "MS", "MSR", "Montserrat"),
	MT = new ISOCountry(0x470, "MT", "MLT", "Malta"),
	MU = new ISOCountry(0x480, "MU", "MUS", "Mauritius"),
	MV = new ISOCountry(0x462, "MV", "MDV", "Maldives"),
	MW = new ISOCountry(0x454, "MW", "MWI", "Malawi"),
	MX = new ISOCountry(0x484, "MX", "MEX", "Mexico"),
	MY = new ISOCountry(0x458, "MY", "MYS", "Malaysia"),
	MZ = new ISOCountry(0x508, "MZ", "MOZ", "Mozambique"),
	NA = new ISOCountry(0x516, "NA", "NAM", "Namibia"),
	NC = new ISOCountry(0x540, "NC", "NCL", "New Caledonia"),
	NE = new ISOCountry(0x562, "NE", "NER", "Niger"),
	NF = new ISOCountry(0x574, "NF", "NFK", "Norfolk Island"),
	NG = new ISOCountry(0x566, "NG", "NGA", "Nigeria"),
	NI = new ISOCountry(0x558, "NI", "NIC", "Nicaragua"),
	NL = new ISOCountry(0x528, "NL", "NLD", "Netherlands"),
	NO = new ISOCountry(0x578, "NO", "NOR", "Norway"),
	NP = new ISOCountry(0x524, "NP", "NPL", "Nepal"),
	NR = new ISOCountry(0x520, "NR", "NRU", "Nauru"),
	NU = new ISOCountry(0x570, "NU", "NIU", "Niue"),
	NZ = new ISOCountry(0x554, "NZ", "NZL", "New Zealand"),
	OM = new ISOCountry(0x512, "OM", "OMN", "Oman"),
	PA = new ISOCountry(0x591, "PA", "PAN", "Panama"),
	PE = new ISOCountry(0x604, "PE", "PER", "Peru"),
	PF = new ISOCountry(0x258, "PF", "PYF", "French Polynesia"),
	PG = new ISOCountry(0x598, "PG", "PNG", "Papua New Guinea"),
	PH = new ISOCountry(0x608, "PH", "PHL", "Philippines"),
	PK = new ISOCountry(0x586, "PK", "PAK", "Pakistan"),
	PL = new ISOCountry(0x616, "PL", "POL", "Poland"),
	PM = new ISOCountry(0x666, "PM", "SPM", "Saint Pierre and Miquelon"),
	PN = new ISOCountry(0x612, "PN", "PCN", "Pitcairn"),
	PR = new ISOCountry(0x630, "PR", "PRI", "Puerto Rico"),
	PS = new ISOCountry(0x275, "PS", "PSE", "Palestinian Territory Occupied"),
	PT = new ISOCountry(0x620, "PT", "PRT", "Portugal"),
	PW = new ISOCountry(0x585, "PW", "PLW", "Palau"),
	PY = new ISOCountry(0x600, "PY", "PRY", "Paraguay"),
	QA = new ISOCountry(0x634, "QA", "QAT", "Qatar"),
	RE = new ISOCountry(0x638, "RE", "REU", "Reunion"),
	RO = new ISOCountry(0x642, "RO", "ROU", "Romania"),
	RS = new ISOCountry(0x688, "RS", "SRB", "Serbia"),
	RU = new ISOCountry(0x643, "RU", "RUS", "Russian Federation"),
	RW = new ISOCountry(0x646, "RW", "RWA", "Rwanda"),
	SA = new ISOCountry(0x682, "SA", "SAU", "Saudi Arabia"),
	SB = new ISOCountry(0x090, "SB", "SLB", "Solomon Islands"),
	SC = new ISOCountry(0x690, "SC", "SYC", "Seychelles"),
	SD = new ISOCountry(0x736, "SD", "SDN", "Sudan"),
	SE = new ISOCountry(0x752, "SE", "SWE", "Sweden"),
	SG = new ISOCountry(0x702, "SG", "SGP", "Singapore"),
	SH = new ISOCountry(0x654, "SH", "SHN", "Saint Helena"),
	SI = new ISOCountry(0x705, "SI", "SVN", "Slovenia"),
	SJ = new ISOCountry(0x744, "SJ", "SJM", "Svalbard and Jan Mayen"),
	SK = new ISOCountry(0x703, "SK", "SVK", "Slovakia"),
	SL = new ISOCountry(0x694, "SL", "SLE", "Sierra Leone"),
	SM = new ISOCountry(0x674, "SM", "SMR", "San Marino"),
	SN = new ISOCountry(0x686, "SN", "SEN", "Senegal"),
	SO = new ISOCountry(0x706, "SO", "SOM", "Somalia"),
	SR = new ISOCountry(0x740, "SR", "SUR", "Suriname"),
	ST = new ISOCountry(0x678, "ST", "STP", "Sao Tome and Principe"),
	SV = new ISOCountry(0x222, "SV", "SLV", "El Salvador"),
	SY = new ISOCountry(0x760, "SY", "SYR", "Syrian Arab Republic"),
	SZ = new ISOCountry(0x748, "SZ", "SWZ", "Swaziland"),
	TC = new ISOCountry(0x796, "TC", "TCA", "Turks and Caicos Islands"),
	TD = new ISOCountry(0x148, "TD", "TCD", "Chad"),
	TF = new ISOCountry(0x260, "TF", "ATF", "French Southern Territories"),
	TG = new ISOCountry(0x768, "TG", "TGO", "Togo"),
	TH = new ISOCountry(0x764, "TH", "THA", "Thailand"),
	TJ = new ISOCountry(0x762, "TJ", "TJK", "Tajikistan"),
	TK = new ISOCountry(0x772, "TK", "TKL", "Tokelau"),
	TL = new ISOCountry(0x626, "TL", "TLS", "Timor-Leste"),
	TM = new ISOCountry(0x795, "TM", "TKM", "Turkmenistan"),
	TN = new ISOCountry(0x788, "TN", "TUN", "Tunisia"),
	TO = new ISOCountry(0x776, "TO", "TON", "Tonga"),
	TR = new ISOCountry(0x792, "TR", "TUR", "Turkey"),
	TT = new ISOCountry(0x780, "TT", "TTO", "Trinidad and Tobago"),
	TV = new ISOCountry(0x798, "TV", "TUV", "Tuvalu"),
	TW = new ISOCountry(0x158, "TW", "TWN", "Taiwan Province of China"),
	TZ = new ISOCountry(0x834, "TZ", "TZA", "Tanzania United Republic of"),
	UA = new ISOCountry(0x804, "UA", "UKR", "Ukraine"),
	UG = new ISOCountry(0x800, "UG", "UGA", "Uganda"),
	UM = new ISOCountry(0x581, "UM", "UMI", "United States Minor Outlying Islands"),
	US = new ISOCountry(0x840, "US", "USA", "United States"),
	UY = new ISOCountry(0x858, "UY", "URY", "Uruguay"),
	UZ = new ISOCountry(0x860, "UZ", "UZB", "Uzbekistan"),
	VA = new ISOCountry(0x336, "VA", "VAT", "Holy See (Vatican City State)"),
	VC = new ISOCountry(0x670, "VC", "VCT", "Saint Vincent and the Grenadines"),
	VE = new ISOCountry(0x862, "VE", "VEN", "Venezuela"),
	VG = new ISOCountry(0x092, "VG", "VGB", "Virgin Islands British"),
	VI = new ISOCountry(0x850, "VI", "VIR", "Virgin Islands U.S."),
	VN = new ISOCountry(0x704, "VN", "VNM", "Viet Nam"),
	VU = new ISOCountry(0x548, "VU", "VUT", "Vanuatu"),
	WF = new ISOCountry(0x876, "WF", "WLF", "Wallis and Futuna"),
	WS = new ISOCountry(0x882, "WS", "WSM", "Samoa"),
	YE = new ISOCountry(0x887, "YE", "YEM", "Yemen"),
	YT = new ISOCountry(0x175, "YT", "MYT", "Mayotte"),
	ZA = new ISOCountry(0x710, "ZA", "ZAF", "South Africa"),
	ZM = new ISOCountry(0x894, "ZM", "ZMB", "Zambia"),
	ZW = new ISOCountry(0x716, "ZW", "ZWE", "Zimbabwe");

	private static final ISOCountry[] VALUES =
	{
		AW, AF, AO, AI, AX, AL, AD, AN, AE, AR, AM, AS, AQ, TF, AG, AU,
		AT, AZ, BI, BE, BJ, BF, BD, BG, BH, BS, BA, BL, BY, BZ, BM, BO,
		BR, BB, BN, BT, BV, BW, CF, CA, CC, CH, CL, CN, CI, CM, CD, CG,
		CK, CO, KM, CV, CR, CU, CX, KY, CY, CZ, DE, DJ, DM, DK, DO, DZ,
		EC, EG, ER, EH, ES, EE, ET, FI, FJ, FK, FR, FO, FM, GA, GB, GE,
		GG, GH, GI, GN, GP, GM, GW, GQ, GR, GD, GL, GT, GF, GU, GY, HK,
		HM, HN, HR, HT, HU, ID, IM, IN, IO, IE, IR, IQ, IS, IL, IT, JM,
		JE, JO, JP, KZ, KE, KG, KH, KI, KN, KR, KW, LA, LB, LR, LY, LC,
		LI, LK, LS, LT, LU, LV, MO, MF, MA, MC, MD, MG, MV, MX, MH, MK,
		ML, MT, MM, ME, MN, MP, MZ, MR, MS, MQ, MU, MW, MY, YT, NA, NC,
		NE, NF, NG, NI, NU, NL, NO, NP, NR, NZ, OM, PK, PA, PN, PE, PH,
		PW, PG, PL, PR, KP, PT, PY, PS, PF, QA, RE, RO, RU, RW, SA, SD,
		SN, SG, GS, SH, SJ, SB, SL, SV, SM, SO, PM, RS, ST, SR, SK, SI,
		SE, SZ, SC, SY, TC, TD, TG, TH, TJ, TK, TM, TL, TO, TT, TN, TR,
		TV, TW, TZ, UG, UA, UM, UY, US, UZ, VA, VC, VE, VG, VI, VN, VU,
		WF, WS, YE, ZA, ZM, ZW
	};

	private int code;
	private String alpha2Code;
	private String alpha3Code;
	private String name;

	private ISOCountry(int code, String alpha2Code, String alpha3Code, String name) {
		this.code = code;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
		this.name = name;
	}

	public static ISOCountry getInstance(int code) {
		for (ISOCountry country: VALUES) {
			if (country.code == code) { return country; }
		}
		throw new IllegalArgumentException("Illegal country code" + Integer.toHexString(code));
	}

	public static ISOCountry getInstance(String code) {
		if (code == null) { throw new IllegalArgumentException("Illegal country code " + code); }
		code = code.trim();
		switch (code.length()) {
		case 2: return fromAlpha2(code);
		case 3: return fromAlpha3(code);
		default: throw new IllegalArgumentException("Illegal country code " + code);
		}
	}

	public static ISOCountry[] values() {
		return VALUES;
	}

	public int valueOf() {
		return code;
	}

	private static ISOCountry fromAlpha2(String code) {
		for (ISOCountry country: VALUES) {
			if (country.alpha2Code.equals(code)) { return country; }
		}
		throw new IllegalArgumentException("Illegal country code " + code);
	}

	private static ISOCountry fromAlpha3(String code) {
		for (ISOCountry country: VALUES) {
			if (country.alpha3Code.equals(code)) { return country; }
		}
		throw new IllegalArgumentException("Illegal country code " + code);
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return alpha2Code;
	}

	public String toAlpha2Code() {
		return alpha2Code;
	}
	
	public String toAlpha3Code() {
		return alpha3Code;
	}
	
	public int hashCode() {
		return code;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (!obj.getClass().equals(this.getClass())) { return false; }
		ISOCountry other = (ISOCountry)obj;
		return other.code == code;
	}
}
