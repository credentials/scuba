/*
 * This file is part of the SCUBA smart card framework.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * Copyright (C) 2009-2013 The SCUBA team.
 *
 * $Id: $
 */

package net.sourceforge.scuba.data;

import java.io.Serializable;

/**
 * ISO 3166 country codes.
 * Table based on Wikipedia information.
 * 
 * This used to be an enum.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: $
 */
public class ISOCountry extends Country implements Serializable {

    private static final long serialVersionUID = 7220597933847617859L;

    /** ISO 3166 country. */
    public static final Country
    AD = new ISOCountry(0x020, "AD", "AND", "Andorra", "Andorran"),
    AE = new ISOCountry(0x784, "AE", "ARE", "United Arab Emirates", "Emirati, Emirian"),
    AF = new ISOCountry(0x004, "AF", "AFG", "Afghanistan", "Afghan"),
    AG = new ISOCountry(0x028, "AG", "ATG", "Antigua and Barbuda", "Antiguan, Barbudan"),
    AI = new ISOCountry(0x660, "AI", "AIA", "Anguilla", "Anguillan"),
    AL = new ISOCountry(0x008, "AL", "ALB", "Albania", "Albanian"),
    AM = new ISOCountry(0x051, "AM", "ARM", "Armenia", "Armenian"),
    AN = new ISOCountry(0x530, "AN", "ANT", "Netherlands Antilles", "Antillean"),
    AO = new ISOCountry(0x024, "AO", "AGO", "Angola", "Angolan"),
    AQ = new ISOCountry(0x010, "AQ", "ATA", "Antarctica", "Antarctic"),
    AR = new ISOCountry(0x032, "AR", "ARG", "Argentina", "Argentine, Argentinean, Argentinian"),
    AS = new ISOCountry(0x016, "AS", "ASM", "American Samoa", "American Samoan"),
    AT = new ISOCountry(0x040, "AT", "AUT", "Austria", "Austrian"),
    AU = new ISOCountry(0x036, "AU", "AUS", "Australia", "Australian"),
    AW = new ISOCountry(0x533, "AW", "ABW", "Aruba", "Aruban"),
    AX = new ISOCountry(0x248, "AX", "ALA", "Aland Islands"),
    AZ = new ISOCountry(0x031, "AZ", "AZE", "Azerbaijan", "Azerbaijani, Azeri"),
    BA = new ISOCountry(0x070, "BA", "BIH", "Bosnia and Herzegovina", "Bosnian, Bosniak, Herzegovinian"),
    BB = new ISOCountry(0x052, "BB", "BRB", "Barbados", "Barbadian"),
    BD = new ISOCountry(0x050, "BD", "BGD", "Bangladesh", "Bangladeshi"),
    BE = new ISOCountry(0x056, "BE", "BEL", "Belgium", "Belgian"),
    BF = new ISOCountry(0x854, "BF", "BFA", "Burkina Faso", "Burkinabe"),
    BG = new ISOCountry(0x100, "BG", "BGR", "Bulgaria", "Bulgarian"),
    BH = new ISOCountry(0x048, "BH", "BHR", "Bahrain", "Bahraini"),
    BI = new ISOCountry(0x108, "BI", "BDI", "Burundi", "Burundian"),
    BJ = new ISOCountry(0x204, "BJ", "BEN", "Benin", "Beninese"),
    BL = new ISOCountry(0x652, "BL", "BLM", "Saint Barthlemy"),
    BM = new ISOCountry(0x060, "BM", "BMU", "Bermuda", "Bermudian, Bermudan"),
    BN = new ISOCountry(0x096, "BN", "BRN", "Brunei Darussalam", "Bruneian"),
    BO = new ISOCountry(0x068, "BO", "BOL", "Bolivia", "Bolivian"),
    BR = new ISOCountry(0x076, "BR", "BRA", "Brazil", "Brazilian"),
    BS = new ISOCountry(0x044, "BS", "BHS", "Bahamas", "Bahamian"),
    BT = new ISOCountry(0x064, "BT", "BTN", "Bhutan", "Bhutanese"),
    BV = new ISOCountry(0x074, "BV", "BVT", "Bouvet Island"),
    BW = new ISOCountry(0x072, "BW", "BWA", "Botswana", "Botswanan"),
    BY = new ISOCountry(0x112, "BY", "BLR", "Belarus", "Belarusian"),
    BZ = new ISOCountry(0x084, "BZ", "BLZ", "Belize", "Belizean"),
    CA = new ISOCountry(0x124, "CA", "CAN", "Canada", "Canadian"),
    CC = new ISOCountry(0x166, "CC", "CCK", "Cocos (Keeling) Islands"),
    CD = new ISOCountry(0x180, "CD", "COD", "Congo the Democratic Republic of the", "Congolese"),
    CF = new ISOCountry(0x140, "CF", "CAF", "Central African Republic", "Central African"),
    CG = new ISOCountry(0x178, "CG", "COG", "Congo", "Congolese"),
    CH = new ISOCountry(0x756, "CH", "CHE", "Switzerland", "Swiss"),
    CI = new ISOCountry(0x384, "CI", "CIV", "Cote d'Ivoire", "Ivorian"),
    CK = new ISOCountry(0x184, "CK", "COK", "Cook Islands"),
    CL = new ISOCountry(0x152, "CL", "CHL", "Chile", "Chilean"),
    CM = new ISOCountry(0x120, "CM", "CMR", "Cameroon", "Cameroonian"),
    CN = new ISOCountry(0x156, "CN", "CHN", "China", "Chinese"),
    CO = new ISOCountry(0x170, "CO", "COL", "Colombia", "Colombian"),
    CR = new ISOCountry(0x188, "CR", "CRI", "Costa Rica", "Costa Rican"),
    CU = new ISOCountry(0x192, "CU", "CUB", "Cuba", "Cuban"),
    CV = new ISOCountry(0x132, "CV", "CPV", "Cape Verde", "Cape Verdean"),
    CX = new ISOCountry(0x162, "CX", "CXR", "Christmas Island"),
    CY = new ISOCountry(0x196, "CY", "CYP", "Cyprus", "Cypriot"),
    CZ = new ISOCountry(0x203, "CZ", "CZE", "Czech Republic", "Czech"),
    DE = new ISOCountry(0x276, "DE", "DEU", "Germany", "German"),
    DJ = new ISOCountry(0x262, "DJ", "DJI", "Djibouti", "Djiboutian"),
    DK = new ISOCountry(0x208, "DK", "DNK", "Denmark", "Danish"),
    DM = new ISOCountry(0x212, "DM", "DMA", "Dominica", "Dominican"),
    DO = new ISOCountry(0x214, "DO", "DOM", "Dominican Republic", "Dominican"),
    DZ = new ISOCountry(0x012, "DZ", "DZA", "Algeria", "Algerian"),
    EC = new ISOCountry(0x218, "EC", "ECU", "Ecuador", "Ecuadorian"),
    EE = new ISOCountry(0x233, "EE", "EST", "Estonia", "Estonian"),
    EG = new ISOCountry(0x818, "EG", "EGY", "Egypt", "Egyptian"),
    EH = new ISOCountry(0x732, "EH", "ESH", "Western Sahara", "Sahraw, Sahrawian, Sahraouian"),
    ER = new ISOCountry(0x232, "ER", "ERI", "Eritrea", "Eritrean"),
    ES = new ISOCountry(0x724, "ES", "ESP", "Spain", "Spanish"),
    ET = new ISOCountry(0x231, "ET", "ETH", "Ethiopia", "Ethiopian"),
    FI = new ISOCountry(0x246, "FI", "FIN", "Finland", "Finnish"),
    FJ = new ISOCountry(0x242, "FJ", "FJI", "Fiji", "Fijian"),
    FK = new ISOCountry(0x238, "FK", "FLK", "Falkland Islands (Malvinas)"),
    FM = new ISOCountry(0x583, "FM", "FSM", "Micronesia Federated States of", "Micronesian"),
    FO = new ISOCountry(0x234, "FO", "FRO", "Faroe Islands", "Faroese"),
    FR = new ISOCountry(0x250, "FR", "FRA", "France", "French"),
    GA = new ISOCountry(0x266, "GA", "GAB", "Gabon", "Gabonese"),
    GB = new ISOCountry(0x826, "GB", "GBR", "United Kingdom", "British"),
    GD = new ISOCountry(0x308, "GD", "GRD", "Grenada", "Grenadian"),
    GE = new ISOCountry(0x268, "GE", "GEO", "Georgia", "Georgian"),
    GF = new ISOCountry(0x254, "GF", "GUF", "French Guiana", "French Guianese"),
    GG = new ISOCountry(0x831, "GG", "GGY", "Guernsey"),
    GH = new ISOCountry(0x288, "GH", "GHA", "Ghana", "Ghanaian"),
    GI = new ISOCountry(0x292, "GI", "GIB", "Gibraltar"),
    GL = new ISOCountry(0x304, "GL", "GRL", "Greenland", "Greenlandic"),
    GM = new ISOCountry(0x270, "GM", "GMB", "Gambia", "Gambian"),
    GN = new ISOCountry(0x324, "GN", "GIN", "Guinea", "Guinean"),
    GP = new ISOCountry(0x312, "GP", "GLP", "Guadeloupe"),
    GQ = new ISOCountry(0x226, "GQ", "GNQ", "Equatorial Guinea", "Equatorial Guinean, Equatoguinean"),
    GR = new ISOCountry(0x300, "GR", "GRC", "Greece", "Greek, Hellenic"),
    GS = new ISOCountry(0x239, "GS", "SGS", "South Georgia and the South Sandwich Islands"),
    GT = new ISOCountry(0x320, "GT", "GTM", "Guatemala", "Guatemalan"),
    GU = new ISOCountry(0x316, "GU", "GUM", "Guam", "Guamanian"),
    GW = new ISOCountry(0x624, "GW", "GNB", "Guinea-Bissau", "Guinean"),
    GY = new ISOCountry(0x328, "GY", "GUY", "Guyana", "Guyanese"),
    HK = new ISOCountry(0x344, "HK", "HKG", "Hong Kong", "Hong Kong, Hongkongese"),
    HM = new ISOCountry(0x334, "HM", "HMD", "Heard Island and McDonald Islands"),
    HN = new ISOCountry(0x340, "HN", "HND", "Honduras", "Honduran"),
    HR = new ISOCountry(0x191, "HR", "HRV", "Croatia", "Croatian"),
    HT = new ISOCountry(0x332, "HT", "HTI", "Haiti", "Haitian"),
    HU = new ISOCountry(0x348, "HU", "HUN", "Hungary", "Hungarian"),
    ID = new ISOCountry(0x360, "ID", "IDN", "Indonesia", "Indonesian"),
    IE = new ISOCountry(0x372, "IE", "IRL", "Ireland", "Irish"),
    IL = new ISOCountry(0x376, "IL", "ISR", "Israel", "Israeli"),
    IM = new ISOCountry(0x833, "IM", "IMN", "Isle of Man", "Manx"),
    IN = new ISOCountry(0x356, "IN", "IND", "India", "Indian"),
    IO = new ISOCountry(0x086, "IO", "IOT", "British Indian Ocean Territory"),
    IQ = new ISOCountry(0x368, "IQ", "IRQ", "Iraq", "Iraqi"),
    IR = new ISOCountry(0x364, "IR", "IRN", "Iran Islamic Republic of", "Iranian, Persian"),
    IS = new ISOCountry(0x352, "IS", "ISL", "Iceland", "Icelandic"),
    IT = new ISOCountry(0x380, "IT", "ITA", "Italy", "Italian"),
    JE = new ISOCountry(0x832, "JE", "JEY", "Jersey"),
    JM = new ISOCountry(0x388, "JM", "JAM", "Jamaica", "Jamaican"),
    JO = new ISOCountry(0x400, "JO", "JOR", "Jordan", "Jordanian"),
    JP = new ISOCountry(0x392, "JP", "JPN", "Japan", "Japanese"),
    KE = new ISOCountry(0x404, "KE", "KEN", "Kenya", "Kenyan"),
    KG = new ISOCountry(0x417, "KG", "KGZ", "Kyrgyzstan", "Kyrgyz"),
    KH = new ISOCountry(0x116, "KH", "KHM", "Cambodia", "Cambodian"),
    KI = new ISOCountry(0x296, "KI", "KIR", "Kiribati", "I-Kiribati"),
    KM = new ISOCountry(0x174, "KM", "COM", "Comoros", "Comorian"),
    KN = new ISOCountry(0x659, "KN", "KNA", "Saint Kitts and Nevis"),
    KP = new ISOCountry(0x408, "KP", "PRK", "Korea Democratic People's Republic of", "North Korean"),
    KR = new ISOCountry(0x410, "KR", "KOR", "Korea Republic of", "South Korean"),
    KW = new ISOCountry(0x414, "KW", "KWT", "Kuwait", "Kuwaiti"),
    KY = new ISOCountry(0x136, "KY", "CYM", "Cayman Islands", "Caymanian"),
    KZ = new ISOCountry(0x398, "KZ", "KAZ", "Kazakhstan", "Kazakh"),
    LA = new ISOCountry(0x418, "LA", "LAO", "Lao People's Democratic Republic", "Lao"),
    LB = new ISOCountry(0x422, "LB", "LBN", "Lebanon", "Lebanese"),
    LC = new ISOCountry(0x662, "LC", "LCA", "Saint Lucia", "Saint Lucian"),
    LI = new ISOCountry(0x438, "LI", "LIE", "Liechtenstein"),
    LK = new ISOCountry(0x144, "LK", "LKA", "Sri Lanka", "Sri Lankan"),
    LR = new ISOCountry(0x430, "LR", "LBR", "Liberia", "Liberian"),
    LS = new ISOCountry(0x426, "LS", "LSO", "Lesotho", "Basotho"),
    LT = new ISOCountry(0x440, "LT", "LTU", "Lithuania", "Lithuanian"),
    LU = new ISOCountry(0x442, "LU", "LUX", "Luxembourg", "Luxembourg, Luxembourgish"),
    LV = new ISOCountry(0x428, "LV", "LVA", "Latvia", "Latvian"),
    LY = new ISOCountry(0x434, "LY", "LBY", "Libyan Arab Jamahiriya", "Libyan"),
    MA = new ISOCountry(0x504, "MA", "MAR", "Morocco", "Moroccan"),
    MC = new ISOCountry(0x492, "MC", "MCO", "Monaco", "Monegasque, Monacan"),
    MD = new ISOCountry(0x498, "MD", "MDA", "Moldova", "Moldovan"),
    ME = new ISOCountry(0x499, "ME", "MNE", "Montenegro", "Montenegrin"),
    MF = new ISOCountry(0x663, "MF", "MAF", "Saint Martin (French part)"),
    MG = new ISOCountry(0x450, "MG", "MDG", "Madagascar", "Malagasy"),
    MH = new ISOCountry(0x584, "MH", "MHL", "Marshall Islands", "Marshallese"),
    MK = new ISOCountry(0x807, "MK", "MKD", "Macedonia the former Yugoslav Republic of", "Macedonian"),
    ML = new ISOCountry(0x466, "ML", "MLI", "Mali", "Malian"),
    MM = new ISOCountry(0x104, "MM", "MMR", "Myanmar", "Burmese"),
    MN = new ISOCountry(0x496, "MN", "MNG", "Mongolia", "Mongolian"),
    MO = new ISOCountry(0x446, "MO", "MAC", "Macao", "Macanese, Chinese"),
    MP = new ISOCountry(0x580, "MP", "MNP", "Northern Mariana Islands", "Northern Marianan"),
    MQ = new ISOCountry(0x474, "MQ", "MTQ", "Martinique", "Martiniquais, Martinican"),
    MR = new ISOCountry(0x478, "MR", "MRT", "Mauritania", "Mauritanian"),
    MS = new ISOCountry(0x500, "MS", "MSR", "Montserrat", "Montserratian"),
    MT = new ISOCountry(0x470, "MT", "MLT", "Malta", "Maltese"),
    MU = new ISOCountry(0x480, "MU", "MUS", "Mauritius", "Mauritian"),
    MV = new ISOCountry(0x462, "MV", "MDV", "Maldives", "Maldivian"),
    MW = new ISOCountry(0x454, "MW", "MWI", "Malawi", "Malawian"),
    MX = new ISOCountry(0x484, "MX", "MEX", "Mexico", "Mexican"),
    MY = new ISOCountry(0x458, "MY", "MYS", "Malaysia", "Malaysian"),
    MZ = new ISOCountry(0x508, "MZ", "MOZ", "Mozambique", "Mozambican"),
    NA = new ISOCountry(0x516, "NA", "NAM", "Namibia", "Namibian"),
    NC = new ISOCountry(0x540, "NC", "NCL", "New Caledonia", "New Caledonian"),
    NE = new ISOCountry(0x562, "NE", "NER", "Niger", "Nigerien"),
    NF = new ISOCountry(0x574, "NF", "NFK", "Norfolk Island"),
    NG = new ISOCountry(0x566, "NG", "NGA", "Nigeria", "Nigerian"),
    NI = new ISOCountry(0x558, "NI", "NIC", "Nicaragua", "Nicaraguan"),
    NL = new ISOCountry(0x528, "NL", "NLD", "Netherlands", "Dutch"),
    NO = new ISOCountry(0x578, "NO", "NOR", "Norway", "Norwegian"),
    NP = new ISOCountry(0x524, "NP", "NPL", "Nepal", "Nepali"),
    NR = new ISOCountry(0x520, "NR", "NRU", "Nauru", "Nauruan"),
    NU = new ISOCountry(0x570, "NU", "NIU", "Niue", "Niuean"),
    NZ = new ISOCountry(0x554, "NZ", "NZL", "New Zealand"),
    OM = new ISOCountry(0x512, "OM", "OMN", "Oman", "Omani"),
    PA = new ISOCountry(0x591, "PA", "PAN", "Panama", "Panamanian"),
    PE = new ISOCountry(0x604, "PE", "PER", "Peru", "Peruvian"),
    PF = new ISOCountry(0x258, "PF", "PYF", "French Polynesia", "French Polynesian"),
    PG = new ISOCountry(0x598, "PG", "PNG", "Papua New Guinea", "Papua New Guinean, Papuan"),
    PH = new ISOCountry(0x608, "PH", "PHL", "Philippines", "Philippine, Filipino"),
    PK = new ISOCountry(0x586, "PK", "PAK", "Pakistan", "Pakistani"),
    PL = new ISOCountry(0x616, "PL", "POL", "Poland", "Polish"),
    PM = new ISOCountry(0x666, "PM", "SPM", "Saint Pierre and Miquelon", "Saint Pierrais, Miquelonnais"),
    PN = new ISOCountry(0x612, "PN", "PCN", "Pitcairn"),
    PR = new ISOCountry(0x630, "PR", "PRI", "Puerto Rico", "Puerto Rican"),
    PS = new ISOCountry(0x275, "PS", "PSE", "Palestinian Territory Occupied", "Palestinian"),
    PT = new ISOCountry(0x620, "PT", "PRT", "Portugal", "Portuguese"),
    PW = new ISOCountry(0x585, "PW", "PLW", "Palau", "Palauan"),
    PY = new ISOCountry(0x600, "PY", "PRY", "Paraguay", "Paraguayan"),
    QA = new ISOCountry(0x634, "QA", "QAT", "Qatar", "Qatari"),
    RE = new ISOCountry(0x638, "RE", "REU", "Reunion", "Reunionese, Reunionnais"),
    RO = new ISOCountry(0x642, "RO", "ROU", "Romania", "Romanian"),
    RS = new ISOCountry(0x688, "RS", "SRB", "Serbia", "Serbian"),
    RU = new ISOCountry(0x643, "RU", "RUS", "Russian Federation", "Russian"),
    RW = new ISOCountry(0x646, "RW", "RWA", "Rwanda", "Rwandan"),
    SA = new ISOCountry(0x682, "SA", "SAU", "Saudi Arabia", "Saudi, Saudi Arabian"),
    SB = new ISOCountry(0x090, "SB", "SLB", "Solomon Islands", "Solomon Island"),
    SC = new ISOCountry(0x690, "SC", "SYC", "Seychelles", "Seychellois"),
    SD = new ISOCountry(0x736, "SD", "SDN", "Sudan", "Sudanese"),
    SE = new ISOCountry(0x752, "SE", "SWE", "Sweden", "Swedish"),
    SG = new ISOCountry(0x702, "SG", "SGP", "Singapore"),
    SH = new ISOCountry(0x654, "SH", "SHN", "Saint Helena", "Saint Helenian"),
    SI = new ISOCountry(0x705, "SI", "SVN", "Slovenia", "Slovenian, Slovene"),
    SJ = new ISOCountry(0x744, "SJ", "SJM", "Svalbard and Jan Mayen"),
    SK = new ISOCountry(0x703, "SK", "SVK", "Slovakia", "Slovak"),
    SL = new ISOCountry(0x694, "SL", "SLE", "Sierra Leone", "Sierra Leonean"),
    SM = new ISOCountry(0x674, "SM", "SMR", "San Marino", "Sammarinese"),
    SN = new ISOCountry(0x686, "SN", "SEN", "Senegal", "Senegalese"),
    SO = new ISOCountry(0x706, "SO", "SOM", "Somalia", "Somali, Somalian"),
    SR = new ISOCountry(0x740, "SR", "SUR", "Suriname", "Surinamese"),
    ST = new ISOCountry(0x678, "ST", "STP", "Sao Tome and Principe", "Sao Tomean"),
    SV = new ISOCountry(0x222, "SV", "SLV", "El Salvador", "Salvadoran"),
    SY = new ISOCountry(0x760, "SY", "SYR", "Syrian Arab Republic", "Syrian"),
    SZ = new ISOCountry(0x748, "SZ", "SWZ", "Swaziland", "Swazi"),
    TC = new ISOCountry(0x796, "TC", "TCA", "Turks and Caicos Islands"),
    TD = new ISOCountry(0x148, "TD", "TCD", "Chad", "Chadian"),
    TF = new ISOCountry(0x260, "TF", "ATF", "French Southern Territories"),
    TG = new ISOCountry(0x768, "TG", "TGO", "Togo", "Togolese"),
    TH = new ISOCountry(0x764, "TH", "THA", "Thailand", "Thai"),
    TJ = new ISOCountry(0x762, "TJ", "TJK", "Tajikistan", "Tajikistani"),
    TK = new ISOCountry(0x772, "TK", "TKL", "Tokelau"),
    TL = new ISOCountry(0x626, "TL", "TLS", "Timor-Leste", "Timorese"),
    TM = new ISOCountry(0x795, "TM", "TKM", "Turkmenistan", "Turkmen"),
    TN = new ISOCountry(0x788, "TN", "TUN", "Tunisia", "Tunisian"),
    TO = new ISOCountry(0x776, "TO", "TON", "Tonga", "Tongan"),
    TR = new ISOCountry(0x792, "TR", "TUR", "Turkey", "Turkish"),
    TT = new ISOCountry(0x780, "TT", "TTO", "Trinidad and Tobago", "Trinidadian, Tobagonian"),
    TV = new ISOCountry(0x798, "TV", "TUV", "Tuvalu", "Tuvaluan"),
    TW = new ISOCountry(0x158, "TW", "TWN", "Taiwan Province of China", "Taiwanese"),
    TZ = new ISOCountry(0x834, "TZ", "TZA", "Tanzania United Republic of", "Tanzanian"),
    UA = new ISOCountry(0x804, "UA", "UKR", "Ukraine", "Ukrainian"),
    UG = new ISOCountry(0x800, "UG", "UGA", "Uganda", "Ugandan"),
    UM = new ISOCountry(0x581, "UM", "UMI", "United States Minor Outlying Islands"),
    US = new ISOCountry(0x840, "US", "USA", "United States", "American"),
    UY = new ISOCountry(0x858, "UY", "URY", "Uruguay", "Uruguayan"),
    UZ = new ISOCountry(0x860, "UZ", "UZB", "Uzbekistan", "Uzbekistani, Uzbek"),
    VA = new ISOCountry(0x336, "VA", "VAT", "Holy See (Vatican City State)"),
    VC = new ISOCountry(0x670, "VC", "VCT", "Saint Vincent and the Grenadines", "Saint Vincentian"),
    VE = new ISOCountry(0x862, "VE", "VEN", "Venezuela", "Venezuelan"),
    VG = new ISOCountry(0x092, "VG", "VGB", "Virgin Islands British", "Virgin Island"),
    VI = new ISOCountry(0x850, "VI", "VIR", "Virgin Islands U.S.", "Virgin Island"),
    VN = new ISOCountry(0x704, "VN", "VNM", "Viet Nam", "Vietnamese"),
    VU = new ISOCountry(0x548, "VU", "VUT", "Vanuatu", "Ni-Vanuatu, Vanuatuan"),
    WF = new ISOCountry(0x876, "WF", "WLF", "Wallis and Futuna", "Wallisian, Futunan"),
    WS = new ISOCountry(0x882, "WS", "WSM", "Samoa", "Samoan"),
    YE = new ISOCountry(0x887, "YE", "YEM", "Yemen", "Yemeni"),
    YT = new ISOCountry(0x175, "YT", "MYT", "Mayotte", "Mahoran"),
    ZA = new ISOCountry(0x710, "ZA", "ZAF", "South Africa", "South African"),
    ZM = new ISOCountry(0x894, "ZM", "ZMB", "Zambia", "Zambian"),
    ZW = new ISOCountry(0x716, "ZW", "ZWE", "Zimbabwe", "Zimbabwean");

    private static final Country[] VALUES =
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
    private String nationality;

    private ISOCountry(int code, String alpha2Code, String alpha3Code, String name) {
        this(code, alpha2Code, alpha3Code, name, name);
    }
    
    private ISOCountry(int code, String alpha2Code, String alpha3Code, String name, String nationality) {
        this.code = code;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.name = name;
        this.nationality = nationality;
    }

    public static Country[] values() {
        return VALUES;
    }

    public int valueOf() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public String getNationality() {
        return nationality;
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

    public int compareTo(Country o) {
        return toAlpha2Code().compareTo(o.toAlpha2Code());
    }
}
