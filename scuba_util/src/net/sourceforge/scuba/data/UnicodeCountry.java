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
 * $Id: UnicodeCountry.java 224 2013-09-09 19:18:03Z martijno $
 */

package net.sourceforge.scuba.data;

import java.io.Serializable;

/**
 * Unicode CLDR country codes.
 * Based on <a href="http://unicode.org/Public/cldr/">http://unicode.org/Public/cldr/</a>, with
 * some additional attributes from ISO 3166 and Wikipedia.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: 224 $
 */
public class UnicodeCountry extends Country implements Serializable {

    private static final long serialVersionUID = 7220597933847617853L;

    public static final Country
//  AC = new UnicodeCountry(-1, "AC", "XXX", "Ascension Island"),
    AD = new UnicodeCountry(0x020, "AD", "AND", "Andorra", "Andorran"),
    AE = new UnicodeCountry(0x784, "AE", "ARE", "United Arab Emirates", "Emirati, Emirian"),
    AF = new UnicodeCountry(0x004, "AF", "AFG", "Afghanistan", "Afghan"),
    AG = new UnicodeCountry(0x028, "AG", "ATG", "Antigua and Barbuda", "Antiguan, Barbudan"),
    AI = new UnicodeCountry(0x660, "AI", "AIA", "Anguilla", "Anguillan"),
    AL = new UnicodeCountry(0x008, "AL", "ALB", "Albania", "Albanian"),
    AM = new UnicodeCountry(0x051, "AM", "ARM", "Armenia", "Armenian"),
    AN = new UnicodeCountry(0x530, "AN", "ANT", "Netherlands Antilles", "Antillean"),
    AO = new UnicodeCountry(0x024, "AO", "AGO", "Angola", "Angolan"),
    AQ = new UnicodeCountry(0x010, "AQ", "ATA", "Antarctica", "Antarctic"),
    AR = new UnicodeCountry(0x032, "AR", "ARG", "Argentina", "Argentine, Argentinean, Argentinian"),
    AS = new UnicodeCountry(0x016, "AS", "ASM", "American Samoa", "American Samoan"),
    AT = new UnicodeCountry(0x040, "AT", "AUT", "Austria", "Austrian"),
    AU = new UnicodeCountry(0x036, "AU", "AUS", "Australia", "Australian"),
    AW = new UnicodeCountry(0x533, "AW", "ABW", "Aruba", "Aruban"),
    AX = new UnicodeCountry(0x248, "AX", "ALA", "Åland Islands", ""),
    AZ = new UnicodeCountry(0x031, "AZ", "AZE", "Azerbaijan", "Azerbaijani, Azeri"),
    BA = new UnicodeCountry(0x070, "BA", "BIH", "Bosnia and Herzegovina", "Bosnian, Bosniak, Herzegovinian"),
    BB = new UnicodeCountry(0x052, "BB", "BRB", "Barbados", "Barbadian"),
    BD = new UnicodeCountry(0x050, "BD", "BGD", "Bangladesh", "Bangladeshi"),
    BE = new UnicodeCountry(0x056, "BE", "BEL", "Belgium", "Belgian"),
    BF = new UnicodeCountry(0x854, "BF", "BFA", "Burkina Faso", "Burkinabe"),
    BG = new UnicodeCountry(0x100, "BG", "BGR", "Bulgaria", "Bulgarian"),
    BH = new UnicodeCountry(0x048, "BH", "BHR", "Bahrain", "Bahraini"),
    BI = new UnicodeCountry(0x108, "BI", "BDI", "Burundi", "Burundian"),
    BJ = new UnicodeCountry(0x204, "BJ", "BEN", "Benin", "Beninese"),
    BL = new UnicodeCountry(0x652, "BL", "BLM", "Saint Barthélemy", ""),
    BM = new UnicodeCountry(0x060, "BM", "BMU", "Bermuda", "Bermudian, Bermudan"),
    BN = new UnicodeCountry(0x096, "BN", "BRN", "Brunei", "Bruneian"),
    BO = new UnicodeCountry(0x068, "BO", "BOL", "Bolivia", "Bolivian"),
//  BQ = new UnicodeCountry(-1, "BQ", "XXX", "Caribbean Netherlands"),
    BR = new UnicodeCountry(0x076, "BR", "BRA", "Brazil", "Brazilian"),
    BS = new UnicodeCountry(0x044, "BS", "BHS", "Bahamas", "Bahamian"),
    BT = new UnicodeCountry(0x064, "BT", "BTN", "Bhutan", "Bhutanese"),
    BV = new UnicodeCountry(0x074, "BV", "BVT", "Bouvet Island", ""),
    BW = new UnicodeCountry(0x072, "BW", "BWA", "Botswana", "Botswanan"),
    BY = new UnicodeCountry(0x112, "BY", "BLR", "Belarus", "Belarusian"),
    BZ = new UnicodeCountry(0x084, "BZ", "BLZ", "Belize", "Belizean"),
    CA = new UnicodeCountry(0x124, "CA", "CAN", "Canada", "Canadian"),
    CC = new UnicodeCountry(0x166, "CC", "CCK", "Cocos [Keeling] Islands", ""),
    CD = new UnicodeCountry(0x180, "CD", "COD", "Congo - Kinshasa", "Congolese"),
    CF = new UnicodeCountry(0x140, "CF", "CAF", "Central African Republic", "Central African"),
    CG = new UnicodeCountry(0x178, "CG", "COG", "Congo - Brazzaville", "Congolese"),
    CH = new UnicodeCountry(0x756, "CH", "CHE", "Switzerland", "Swiss"),
    CI = new UnicodeCountry(0x384, "CI", "CIV", "Côte d’Ivoire", "Ivorian"),
    CK = new UnicodeCountry(0x184, "CK", "COK", "Cook Islands", ""),
    CL = new UnicodeCountry(0x152, "CL", "CHL", "Chile", "Chilean"),
    CM = new UnicodeCountry(0x120, "CM", "CMR", "Cameroon", "Cameroonian"),
    CN = new UnicodeCountry(0x156, "CN", "CHN", "China", "Chinese"),
    CO = new UnicodeCountry(0x170, "CO", "COL", "Colombia", "Colombian"),
//  CP = new UnicodeCountry(-1, "CP", "XXX", "Clipperton Island"),
    CR = new UnicodeCountry(0x188, "CR", "CRI", "Costa Rica", "Costa Rican"),
    CU = new UnicodeCountry(0x192, "CU", "CUB", "Cuba", "Cuban"),
    CV = new UnicodeCountry(0x132, "CV", "CPV", "Cape Verde", "Cape Verdean"),
//  CW = new UnicodeCountry(-1, "CW", "XXX", "Curaçao"),
    CX = new UnicodeCountry(0x162, "CX", "CXR", "Christmas Island", ""),
    CY = new UnicodeCountry(0x196, "CY", "CYP", "Cyprus", "Cypriot"),
    CZ = new UnicodeCountry(0x203, "CZ", "CZE", "Czech Republic", "Czech"),
    DE = new UnicodeCountry(0x276, "DE", "DEU", "Germany", "German"),
//  DG = new UnicodeCountry(-1, "DG", "XXX", "Diego Garcia"),
    DJ = new UnicodeCountry(0x262, "DJ", "DJI", "Djibouti", "Djiboutian"),
    DK = new UnicodeCountry(0x208, "DK", "DNK", "Denmark", "Danish"),
    DM = new UnicodeCountry(0x212, "DM", "DMA", "Dominica", "Dominican"),
    DO = new UnicodeCountry(0x214, "DO", "DOM", "Dominican Republic", "Dominican"),
    DZ = new UnicodeCountry(0x012, "DZ", "DZA", "Algeria", "Algerian"),
//  EA = new UnicodeCountry(-1, "EA", "XXX", "Ceuta and Melilla"),
    EC = new UnicodeCountry(0x218, "EC", "ECU", "Ecuador", "Ecuadorian"),
    EE = new UnicodeCountry(0x233, "EE", "EST", "Estonia", "Estonian"),
    EG = new UnicodeCountry(0x818, "EG", "EGY", "Egypt", "Egyptian"),
    EH = new UnicodeCountry(0x732, "EH", "ESH", "Western Sahara", "Sahraw, Sahrawian, Sahraouian"),
    ER = new UnicodeCountry(0x232, "ER", "ERI", "Eritrea", "Eritrean"),
    ES = new UnicodeCountry(0x724, "ES", "ESP", "Spain", "Spanish"),
    ET = new UnicodeCountry(0x231, "ET", "ETH", "Ethiopia", "Ethiopian"),
//  EU = new UnicodeCountry(-1, "EU", "XXX", "European Union"),
    FI = new UnicodeCountry(0x246, "FI", "FIN", "Finland", "Finnish"),
    FJ = new UnicodeCountry(0x242, "FJ", "FJI", "Fiji", "Fijian"),
    FK = new UnicodeCountry(0x238, "FK", "FLK", "Falkland Islands", ""),
    FM = new UnicodeCountry(0x583, "FM", "FSM", "Micronesia", "Micronesian"),
    FO = new UnicodeCountry(0x234, "FO", "FRO", "Faroe Islands", "Faroese"),
    FR = new UnicodeCountry(0x250, "FR", "FRA", "France", "French"),
    GA = new UnicodeCountry(0x266, "GA", "GAB", "Gabon", "Gabonese"),
    GB = new UnicodeCountry(0x826, "GB", "GBR", "United Kingdom", "British"),
    GD = new UnicodeCountry(0x308, "GD", "GRD", "Grenada", "Grenadian"),
    GE = new UnicodeCountry(0x268, "GE", "GEO", "Georgia", "Georgian"),
    GF = new UnicodeCountry(0x254, "GF", "GUF", "French Guiana", "French Guianese"),
    GG = new UnicodeCountry(0x831, "GG", "GGY", "Guernsey", ""),
    GH = new UnicodeCountry(0x288, "GH", "GHA", "Ghana", "Ghanaian"),
    GI = new UnicodeCountry(0x292, "GI", "GIB", "Gibraltar", ""),
    GL = new UnicodeCountry(0x304, "GL", "GRL", "Greenland", "Greenlandic"),
    GM = new UnicodeCountry(0x270, "GM", "GMB", "Gambia", "Gambian"),
    GN = new UnicodeCountry(0x324, "GN", "GIN", "Guinea", "Guinean"),
    GP = new UnicodeCountry(0x312, "GP", "GLP", "Guadeloupe", ""),
    GQ = new UnicodeCountry(0x226, "GQ", "GNQ", "Equatorial Guinea", "Equatorial Guinean, Equatoguinean"),
    GR = new UnicodeCountry(0x300, "GR", "GRC", "Greece", "Greek, Hellenic"),
    GS = new UnicodeCountry(0x239, "GS", "SGS", "South Georgia and the South Sandwich Islands", ""),
    GT = new UnicodeCountry(0x320, "GT", "GTM", "Guatemala", "Guatemalan"),
    GU = new UnicodeCountry(0x316, "GU", "GUM", "Guam", "Guamanian"),
    GW = new UnicodeCountry(0x624, "GW", "GNB", "Guinea-Bissau", "Guinean"),
    GY = new UnicodeCountry(0x328, "GY", "GUY", "Guyana", "Guyanese"),
    HK = new UnicodeCountry(0x344, "HK", "HKG", "Hong Kong SAR China", "Hong Kong, Hongkongese"),
    HM = new UnicodeCountry(0x334, "HM", "HMD", "Heard Island and McDonald Islands", ""),
    HN = new UnicodeCountry(0x340, "HN", "HND", "Honduras", "Honduran"),
    HR = new UnicodeCountry(0x191, "HR", "HRV", "Croatia", "Croatian"),
    HT = new UnicodeCountry(0x332, "HT", "HTI", "Haiti", "Haitian"),
    HU = new UnicodeCountry(0x348, "HU", "HUN", "Hungary", "Hungarian"),
//  IC = new UnicodeCountry(-1, "IC", "XXX", "Canary Islands"),
    ID = new UnicodeCountry(0x360, "ID", "IDN", "Indonesia", "Indonesian"),
    IE = new UnicodeCountry(0x372, "IE", "IRL", "Ireland", "Irish"),
    IL = new UnicodeCountry(0x376, "IL", "ISR", "Israel", "Israeli"),
    IM = new UnicodeCountry(0x833, "IM", "IMN", "Isle of Man", "Manx"),
    IN = new UnicodeCountry(0x356, "IN", "IND", "India", "Indian"),
    IO = new UnicodeCountry(0x086, "IO", "IOT", "British Indian Ocean Territory", ""),
    IQ = new UnicodeCountry(0x368, "IQ", "IRQ", "Iraq", "Iraqi"),
    IR = new UnicodeCountry(0x364, "IR", "IRN", "Iran", "Iranian, Persian"),
    IS = new UnicodeCountry(0x352, "IS", "ISL", "Iceland", "Icelandic"),
    IT = new UnicodeCountry(0x380, "IT", "ITA", "Italy", "Italian"),
    JE = new UnicodeCountry(0x832, "JE", "JEY", "Jersey", ""),
    JM = new UnicodeCountry(0x388, "JM", "JAM", "Jamaica", "Jamaican"),
    JO = new UnicodeCountry(0x400, "JO", "JOR", "Jordan", "Jordanian"),
    JP = new UnicodeCountry(0x392, "JP", "JPN", "Japan", "Japanese"),
    KE = new UnicodeCountry(0x404, "KE", "KEN", "Kenya", "Kenyan"),
    KG = new UnicodeCountry(0x417, "KG", "KGZ", "Kyrgyzstan", "Kyrgyz"),
    KH = new UnicodeCountry(0x116, "KH", "KHM", "Cambodia", "Cambodian"),
    KI = new UnicodeCountry(0x296, "KI", "KIR", "Kiribati", "I-Kiribati"),
    KM = new UnicodeCountry(0x174, "KM", "COM", "Comoros", "Comorian"),
    KN = new UnicodeCountry(0x659, "KN", "KNA", "Saint Kitts and Nevis", ""),
    KP = new UnicodeCountry(0x408, "KP", "PRK", "North Korea", "North Korean"),
    KR = new UnicodeCountry(0x410, "KR", "KOR", "South Korea", "South Korean"),
    KW = new UnicodeCountry(0x414, "KW", "KWT", "Kuwait", "Kuwaiti"),
    KY = new UnicodeCountry(0x136, "KY", "CYM", "Cayman Islands", "Caymanian"),
    KZ = new UnicodeCountry(0x398, "KZ", "KAZ", "Kazakhstan", "Kazakh"),
    LA = new UnicodeCountry(0x418, "LA", "Lao", "Laos", "Lao"),
    LB = new UnicodeCountry(0x422, "LB", "LBN", "Lebanon", "Lebanese"),
    LC = new UnicodeCountry(0x662, "LC", "LCA", "Saint Lucia", "Saint Lucian"),
    LI = new UnicodeCountry(0x438, "LI", "LIE", "Liechtenstein", ""),
    LK = new UnicodeCountry(0x144, "LK", "LKA", "Sri Lanka", "Sri Lankan"),
    LR = new UnicodeCountry(0x430, "LR", "LBR", "Liberia", "Liberian"),
    LS = new UnicodeCountry(0x426, "LS", "LSO", "Lesotho", "Basotho"),
    LT = new UnicodeCountry(0x440, "LT", "LTU", "Lithuania", "Lithuanian"),
    LU = new UnicodeCountry(0x442, "LU", "LUX", "Luxembourg", "Luxembourg, Luxembourgish"),
    LV = new UnicodeCountry(0x428, "LV", "LVA", "Latvia", "Latvian"),
    LY = new UnicodeCountry(0x434, "LY", "LBY", "Libya", "Libyan"),
    MA = new UnicodeCountry(0x504, "MA", "MAR", "Morocco", "Moroccan"),
    MC = new UnicodeCountry(0x492, "MC", "MCO", "Monaco", "Monegasque, Monacan"),
    MD = new UnicodeCountry(0x498, "MD", "MDA", "Moldova", "Moldovan"),
    ME = new UnicodeCountry(0x499, "ME", "MNE", "Montenegro", "Montenegrin"),
    MF = new UnicodeCountry(0x663, "MF", "MAF", "Saint Martin", ""),
    MG = new UnicodeCountry(0x450, "MG", "MDG", "Madagascar", "Malagasy"),
    MH = new UnicodeCountry(0x584, "MH", "MHL", "Marshall Islands", "Marshallese"),
    MK = new UnicodeCountry(0x807, "MK", "MKD", "Macedonia", "Macedonian"),
    ML = new UnicodeCountry(0x466, "ML", "MLI", "Mali", "Malian"),
    MM = new UnicodeCountry(0x104, "MM", "MMR", "Myanmar [Burma]", "Burmese"),
    MN = new UnicodeCountry(0x496, "MN", "MNG", "Mongolia", "Mongolian"),
    MO = new UnicodeCountry(0x446, "MO", "MAC", "Macau SAR China", "Macanese, Chinese"),
    MP = new UnicodeCountry(0x580, "MP", "MNP", "Northern Mariana Islands", "Northern Marianan"),
    MQ = new UnicodeCountry(0x474, "MQ", "MTQ", "Martinique", "Martiniquais, Martinican"),
    MR = new UnicodeCountry(0x478, "MR", "MRT", "Mauritania", "Mauritanian"),
    MS = new UnicodeCountry(0x500, "MS", "MSR", "Montserrat", "Montserratian"),
    MT = new UnicodeCountry(0x470, "MT", "MLT", "Malta", "Maltese"),
    MU = new UnicodeCountry(0x480, "MU", "MUS", "Mauritius", "Mauritian"),
    MV = new UnicodeCountry(0x462, "MV", "MDV", "Maldives", "Maldivian"),
    MW = new UnicodeCountry(0x454, "MW", "MWI", "Malawi", "Malawian"),
    MX = new UnicodeCountry(0x484, "MX", "MEX", "Mexico", "Mexican"),
    MY = new UnicodeCountry(0x458, "MY", "MYS", "Malaysia", "Malaysian"),
    MZ = new UnicodeCountry(0x508, "MZ", "MOZ", "Mozambique", "Mozambican"),
    NA = new UnicodeCountry(0x516, "NA", "NAM", "Namibia", "Namibian"),
    NC = new UnicodeCountry(0x540, "NC", "NCL", "New Caledonia", "New Caledonian"),
    NE = new UnicodeCountry(0x562, "NE", "NER", "Niger", "Nigerien"),
    NF = new UnicodeCountry(0x574, "NF", "NFK", "Norfolk Island", ""),
    NG = new UnicodeCountry(0x566, "NG", "NGA", "Nigeria", "Nigerian"),
    NI = new UnicodeCountry(0x558, "NI", "NIC", "Nicaragua", "Nicaraguan"),
    NL = new UnicodeCountry(0x528, "NL", "NLD", "Netherlands", "Dutch"),
    NO = new UnicodeCountry(0x578, "NO", "NOR", "Norway", "Norwegian"),
    NP = new UnicodeCountry(0x524, "NP", "NPL", "Nepal", "Nepali"),
    NR = new UnicodeCountry(0x520, "NR", "NRU", "Nauru", "Nauruan"),
    NU = new UnicodeCountry(0x570, "NU", "NIU", "Niue", "Niuean"),
    NZ = new UnicodeCountry(0x554, "NZ", "NZL", "New Zealand", ""),
    OM = new UnicodeCountry(0x512, "OM", "OMN", "Oman", "Omani"),
    PA = new UnicodeCountry(0x591, "PA", "PAN", "Panama", "Panamanian"),
    PE = new UnicodeCountry(0x604, "PE", "PER", "Peru", "Peruvian"),
    PF = new UnicodeCountry(0x258, "PF", "PYF", "French Polynesia", "French Polynesian"),
    PG = new UnicodeCountry(0x598, "PG", "PNG", "Papua New Guinea", "Papua New Guinean, Papuan"),
    PH = new UnicodeCountry(0x608, "PH", "PHL", "Philippines", "Philippine, Filipino"),
    PK = new UnicodeCountry(0x586, "PK", "PAK", "Pakistan", "Pakistani"),
    PL = new UnicodeCountry(0x616, "PL", "POL", "Poland", "Polish"),
    PM = new UnicodeCountry(0x666, "PM", "SPM", "Saint Pierre and Miquelon", "Saint Pierrais, Miquelonnais"),
    PN = new UnicodeCountry(0x612, "PN", "PCN", "Pitcairn Islands", ""),
    PR = new UnicodeCountry(0x630, "PR", "PRI", "Puerto Rico", "Puerto Rican"),
    PS = new UnicodeCountry(0x275, "PS", "PSE", "Palestinian Territories", "Palestinian"),
    PT = new UnicodeCountry(0x620, "PT", "PRT", "Portugal", "Portuguese"),
    PW = new UnicodeCountry(0x585, "PW", "PLW", "Palau", "Palauan"),
    PY = new UnicodeCountry(0x600, "PY", "PRY", "Paraguay", "Paraguayan"),
    QA = new UnicodeCountry(0x634, "QA", "QAT", "Qatar", "Qatari"),
//  QO = new UnicodeCountry(-1, "QO", "XXX", "Outlying Oceania"),
    RE = new UnicodeCountry(0x638, "RE", "REU", "Réunion", "Reunionese, Reunionnais"),
    RO = new UnicodeCountry(0x642, "RO", "ROU", "Romania", "Romanian"),
    RS = new UnicodeCountry(0x688, "RS", "SRB", "Serbia", "Serbian"),
    RU = new UnicodeCountry(0x643, "RU", "RUS", "Russia", "Russian"),
    RW = new UnicodeCountry(0x646, "RW", "RWA", "Rwanda", "Rwandan"),
    SA = new UnicodeCountry(0x682, "SA", "SAU", "Saudi Arabia", "Saudi, Saudi Arabian"),
    SB = new UnicodeCountry(0x090, "SB", "SLB", "Solomon Islands", "Solomon Island"),
    SC = new UnicodeCountry(0x690, "SC", "SYC", "Seychelles", "Seychellois"),
    SD = new UnicodeCountry(0x736, "SD", "SDN", "Sudan", "Sudanese"),
    SE = new UnicodeCountry(0x752, "SE", "SWE", "Sweden", "Swedish"),
    SG = new UnicodeCountry(0x702, "SG", "SGP", "Singapore", ""),
    SH = new UnicodeCountry(0x654, "SH", "SHN", "Saint Helena", "Saint Helenian"),
    SI = new UnicodeCountry(0x705, "SI", "SVN", "Slovenia", "Slovenian, Slovene"),
    SJ = new UnicodeCountry(0x744, "SJ", "SJM", "Svalbard and Jan Mayen", ""),
    SK = new UnicodeCountry(0x703, "SK", "SVK", "Slovakia", "Slovak"),
    SL = new UnicodeCountry(0x694, "SL", "SLE", "Sierra Leone", "Sierra Leonean"),
    SM = new UnicodeCountry(0x674, "SM", "SMR", "San Marino", "Sammarinese"),
    SN = new UnicodeCountry(0x686, "SN", "SEN", "Senegal", "Senegalese"),
    SO = new UnicodeCountry(0x706, "SO", "SOM", "Somalia", "Somali, Somalian"),
    SR = new UnicodeCountry(0x740, "SR", "SUR", "Suriname", "Surinamese"),
//  SS = new UnicodeCountry(-1, "SS", "XXX", "South Sudan"),
    ST = new UnicodeCountry(0x678, "ST", "STP", "São Tomé and Príncipe", "Sao Tomean"),
    SV = new UnicodeCountry(0x222, "SV", "SLV", "El Salvador", "Salvadoran"),
//  SX = new UnicodeCountry(-1, "SX", "XXX", "Sint Maarten"),
    SY = new UnicodeCountry(0x760, "SY", "SYR", "Syria", "Syrian"),
    SZ = new UnicodeCountry(0x748, "SZ", "SWZ", "Swaziland", "Swazi"),
//  TA = new UnicodeCountry(-1, "TA", "XXX", "Tristan da Cunha"),
    TC = new UnicodeCountry(0x796, "TC", "TCA", "Turks and Caicos Islands", ""),
    TD = new UnicodeCountry(0x148, "TD", "TCD", "Chad", "Chadian"),
    TF = new UnicodeCountry(0x260, "TF", "ATF", "French Southern Territories", ""),
    TG = new UnicodeCountry(0x768, "TG", "TGO", "Togo", "Togolese"),
    TH = new UnicodeCountry(0x764, "TH", "THA", "Thailand", "Thai"),
    TJ = new UnicodeCountry(0x762, "TJ", "TJK", "Tajikistan", "Tajikistani"),
    TK = new UnicodeCountry(0x772, "TK", "TKL", "Tokelau", ""),
    TL = new UnicodeCountry(0x626, "TL", "TLS", "Timor-Leste", "Timorese"),
    TM = new UnicodeCountry(0x795, "TM", "TKM", "Turkmenistan", "Turkmen"),
    TN = new UnicodeCountry(0x788, "TN", "TUN", "Tunisia", "Tunisian"),
    TO = new UnicodeCountry(0x776, "TO", "TON", "Tonga", "Tongan"),
    TR = new UnicodeCountry(0x792, "TR", "TUR", "Turkey", "Turkish"),
    TT = new UnicodeCountry(0x780, "TT", "TTO", "Trinidad and Tobago", "Trinidadian, Tobagonian"),
    TV = new UnicodeCountry(0x798, "TV", "TUV", "Tuvalu", "Tuvaluan"),
    TW = new UnicodeCountry(0x158, "TW", "TWN", "Taiwan", "Taiwanese"),
    TZ = new UnicodeCountry(0x834, "TZ", "TZA", "Tanzania", "Tanzanian"),
    UA = new UnicodeCountry(0x804, "UA", "UKR", "Ukraine", "Ukrainian"),
    UG = new UnicodeCountry(0x800, "UG", "UGA", "Uganda", "Ugandan"),
    UM = new UnicodeCountry(0x581, "UM", "UMI", "U.S. Outlying Islands", ""),
    US = new UnicodeCountry(0x840, "US", "USA", "United States", "American"),
    UY = new UnicodeCountry(0x858, "UY", "URY", "Uruguay", "Uruguayan"),
    UZ = new UnicodeCountry(0x860, "UZ", "UZB", "Uzbekistan", "Uzbekistani, Uzbek"),
    VA = new UnicodeCountry(0x336, "VA", "VAT", "Vatican City", ""),
    VC = new UnicodeCountry(0x670, "VC", "VCT", "Saint Vincent and the Grenadines", "Saint Vincentian"),
    VE = new UnicodeCountry(0x862, "VE", "VEN", "Venezuela", "Venezuelan"),
    VG = new UnicodeCountry(0x092, "VG", "VGB", "British Virgin Islands", "Virgin Island"),
    VI = new UnicodeCountry(0x850, "VI", "VIR", "U.S. Virgin Islands", "Virgin Island"),
    VN = new UnicodeCountry(0x704, "VN", "VNM", "Vietnam", "Vietnamese"),
    VU = new UnicodeCountry(0x548, "VU", "VUT", "Vanuatu", "Ni-Vanuatu, Vanuatuan"),
    WF = new UnicodeCountry(0x876, "WF", "WLF", "Wallis and Futuna", "Wallisian, Futunan"),
    WS = new UnicodeCountry(0x882, "WS", "WSM", "Samoa", "Samoan"),
//  XK = new UnicodeCountry(-1, "XK", "XXX", "Kosovo"),
    YE = new UnicodeCountry(0x887, "YE", "YEM", "Yemen", "Yemeni"),
    YT = new UnicodeCountry(0x175, "YT", "MYT", "Mayotte", "Mahoran"),
    ZA = new UnicodeCountry(0x710, "ZA", "ZAF", "South Africa", "South African"),
    ZM = new UnicodeCountry(0x894, "ZM", "ZMB", "Zambia", "Zambian"),
    ZW = new UnicodeCountry(0x716, "ZW", "ZWE", "Zimbabwe", "Zimbabwean");
    
    private static final Country[] VALUES =
    {
        /* AC, */ AD, AE, AF, AG, AI, AL, AM, AN, AO, AQ, AR, AS, AT, AU, AW,
        AX, AZ, BA, BB, BD, BE, BF, BG, BH, BI, BJ, BL, BM, BN, BO, /* BQ, */
        BR, BS, BT, BV, BW, BY, BZ, CA, CC, CD, CF, CG, CH, CI, CK, CL,
        CM, CN, CO, /* CP, */ CR, CU, CV, /* CW, */ CX, CY, CZ, DE, /* DG, */ DJ, DK, DM,
        DO, DZ, /* EA, */ EC, EE, EG, EH, ER, ES, ET, /* EU, */ FI, FJ, FK, FM, FO,
        FR, GA, GB, GD, GE, GF, GG, GH, GI, GL, GM, GN, GP, GQ, GR, GS,
        GT, GU, GW, GY, HK, HM, HN, HR, HT, HU, /* IC, */ ID, IE, IL, IM, IN,
        IO, IQ, IR, IS, IT, JE, JM, JO, JP, KE, KG, KH, KI, KM, KN, KP,
        KR, KW, KY, KZ, LA, LB, LC, LI, LK, LR, LS, LT, LU, LV, LY, MA,
        MC, MD, ME, MF, MG, MH, MK, ML, MM, MN, MO, MP, MQ, MR, MS, MT,
        MU, MV, MW, MX, MY, MZ, NA, NC, NE, NF, NG, NI, NL, NO, NP, NR,
        NU, NZ, OM, PA, PE, PF, PG, PH, PK, PL, PM, PN, PR, PS, PT, PW,
        PY, QA, /* QO, */ RE, RO, RS, RU, RW, SA, SB, SC, SD, SE, SG, SH, SI,
        SJ, SK, SL, SM, SN, SO, SR, /* SS, */ ST, SV, /* SX, */ SY, SZ, /* TA, */ TC, TD,
        TF, TG, TH, TJ, TK, TL, TM, TN, TO, TR, TT, TV, TW, TZ, UA, UG,
        UM, US, UY, UZ, VA, VC, VE, VG, VI, VN, VU, WF, WS, /* XK, */ YE, YT,
        ZA, ZM, ZW
    };

    private int code;
    private String alpha2Code;
    private String alpha3Code;
    private String name;
    private String nationality;
    
    private UnicodeCountry(int code, String alpha2Code, String alpha3Code, String name) {
        this(code, alpha2Code, alpha3Code, name, name);
    }
    
    private UnicodeCountry(int code, String alpha2Code, String alpha3Code, String name, String nationality) {
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
        UnicodeCountry other = (UnicodeCountry)obj;
        return other.code == code;
    }

    public int compareTo(Country o) {
        return toAlpha2Code().compareTo(o.toAlpha2Code());
    }
}
