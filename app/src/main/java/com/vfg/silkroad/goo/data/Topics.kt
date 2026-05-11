package com.vfg.silkroad.goo.data

import androidx.compose.ui.graphics.Color
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.NeonPink
import com.vfg.silkroad.goo.ui.theme.NeonRed
import com.vfg.silkroad.goo.ui.theme.NeonViolet

enum class Visual {
    Atom, Shells, Molecule, DoubleBond, PhScale, EnergyExo, EnergyEndo,
    Reaction, PeriodicCell, BondIonic, BondCovalent, BondMetallic, Electron
}

data class Subtopic(
    val id: String,
    val title: String,
    val intro: String,
    val keyFacts: List<String>,
    val examples: List<String>,
    val visual: Visual
)

data class Topic(
    val id: String,
    val title: String,
    val description: String,
    val accent: Color,
    val cardVisual: Visual,
    val subtopics: List<Subtopic>
)

object TopicsRepo {

    val all: List<Topic> = listOf(
        // 1
        Topic(
            id = "atomic_structure",
            title = "Atomic Structure",
            description = "How atoms are built from a tiny dense nucleus and a cloud of electrons.",
            accent = ElectricCyan,
            cardVisual = Visual.Atom,
            subtopics = listOf(
                Subtopic(
                    id = "pne",
                    title = "Protons, Neutrons & Electrons",
                    intro = "An atom is built from three subatomic particles. Protons and neutrons sit packed together inside the nucleus, while electrons move around it in regions of probability called orbitals.",
                    keyFacts = listOf(
                        "Protons carry a +1 charge; electrons carry a −1 charge; neutrons are uncharged.",
                        "A proton and a neutron each have a mass of about 1 atomic mass unit (u).",
                        "An electron is roughly 1/1836 of the mass of a proton — almost massless by comparison.",
                        "A neutral atom has the same number of protons and electrons.",
                        "Almost all of the atom's mass is concentrated in the nucleus."
                    ),
                    examples = listOf(
                        "A neutral helium atom has 2 protons, 2 neutrons, and 2 electrons.",
                        "An aluminum atom has 13 protons, 14 neutrons, and 13 electrons."
                    ),
                    visual = Visual.Atom
                ),
                Subtopic(
                    id = "atomic_number_mass",
                    title = "Atomic Number & Mass",
                    intro = "The atomic number identifies which element you are looking at, while the mass number describes how heavy a particular atom is.",
                    keyFacts = listOf(
                        "Atomic number (Z) = number of protons in the nucleus.",
                        "Mass number (A) = protons + neutrons.",
                        "The atomic number is what defines the element — change Z and you change the element.",
                        "Number of neutrons = A − Z.",
                        "On the periodic table, the small whole number is Z; the average atomic mass is shown below the symbol."
                    ),
                    examples = listOf(
                        "Carbon-12 has Z = 6 and A = 12, so it has 6 neutrons.",
                        "Uranium-238 has Z = 92 and A = 238, so it has 146 neutrons."
                    ),
                    visual = Visual.PeriodicCell
                ),
                Subtopic(
                    id = "shells",
                    title = "Electron Shells",
                    intro = "Electrons occupy energy levels (shells) around the nucleus. Each shell can hold only a limited number of electrons before the next shell starts to fill.",
                    keyFacts = listOf(
                        "Maximum electrons per shell follows 2n² (2, 8, 18, 32, …).",
                        "Lower shells are filled first because they have lower energy.",
                        "The outermost shell holds the valence electrons that drive chemistry.",
                        "An atom with a full outer shell is especially stable — like the noble gases.",
                        "Shells are split into subshells (s, p, d, f) with characteristic shapes."
                    ),
                    examples = listOf(
                        "Sodium (Z = 11) fills as 2, 8, 1 — one valence electron, very reactive.",
                        "Argon (Z = 18) fills as 2, 8, 8 — full outer shell, extremely unreactive."
                    ),
                    visual = Visual.Shells
                ),
                Subtopic(
                    id = "isotopes",
                    title = "Isotopes",
                    intro = "Isotopes are atoms of the same element with different numbers of neutrons. They behave the same chemically but have different masses, and some are radioactive.",
                    keyFacts = listOf(
                        "Same atomic number, different mass number.",
                        "Chemical properties are nearly identical — chemistry is set by electrons.",
                        "Some isotopes are stable; others are unstable and decay over time.",
                        "Average atomic mass on the periodic table is a weighted average of isotopes.",
                        "Half-life describes how quickly an unstable isotope decays."
                    ),
                    examples = listOf(
                        "Carbon-12 (stable) and carbon-14 (radioactive, used in dating).",
                        "Hydrogen-1 (protium), hydrogen-2 (deuterium), hydrogen-3 (tritium)."
                    ),
                    visual = Visual.Atom
                ),
                Subtopic(
                    id = "ions",
                    title = "Ions",
                    intro = "When an atom gains or loses electrons it becomes a charged particle called an ion. Ions are the building blocks of ionic compounds and electrolyte solutions.",
                    keyFacts = listOf(
                        "Lose electrons → positive ion (cation).",
                        "Gain electrons → negative ion (anion).",
                        "The number of protons does not change when ions form.",
                        "Atoms tend to gain or lose electrons to reach a noble-gas configuration.",
                        "Charge of an ion is written as a superscript: Na⁺, Cl⁻, Mg²⁺."
                    ),
                    examples = listOf(
                        "Sodium loses 1 electron to become Na⁺.",
                        "Oxygen gains 2 electrons to become O²⁻."
                    ),
                    visual = Visual.Shells
                )
            )
        ),
        // 2
        Topic(
            id = "periodic_table",
            title = "Periodic Table",
            description = "How elements are organized so chemistry becomes predictable.",
            accent = AcidGreen,
            cardVisual = Visual.PeriodicCell,
            subtopics = listOf(
                Subtopic(
                    id = "groups_periods",
                    title = "Groups & Periods",
                    intro = "The periodic table is organized into vertical columns (groups) and horizontal rows (periods). Elements in the same group share similar chemical behavior.",
                    keyFacts = listOf(
                        "There are 18 groups and 7 periods.",
                        "Group number tells you how many valence electrons elements typically have.",
                        "Across a period, elements gain protons and become less metallic.",
                        "Down a group, atoms get larger and outer electrons are held more loosely.",
                        "Group 1 = alkali metals; Group 17 = halogens; Group 18 = noble gases."
                    ),
                    examples = listOf(
                        "Lithium, sodium, and potassium are all in Group 1 and react vigorously with water.",
                        "Fluorine and chlorine are both halogens and form −1 ions easily."
                    ),
                    visual = Visual.PeriodicCell
                ),
                Subtopic(
                    id = "metals_nonmetals",
                    title = "Metals and Nonmetals",
                    intro = "Most elements are metals, sitting on the left and middle of the table. Nonmetals occupy the upper right, with metalloids stepping along the staircase between them.",
                    keyFacts = listOf(
                        "Metals are shiny, conductive, malleable, and tend to lose electrons.",
                        "Nonmetals are usually dull insulators and tend to gain electrons.",
                        "Metalloids (B, Si, Ge, As, Sb, Te) have intermediate properties.",
                        "Most metals are solid at room temperature; mercury is a liquid.",
                        "Metals form positive ions; nonmetals usually form negative ions."
                    ),
                    examples = listOf(
                        "Copper conducts electricity in wires and is highly malleable.",
                        "Sulfur is a brittle yellow solid that does not conduct electricity."
                    ),
                    visual = Visual.PeriodicCell
                ),
                Subtopic(
                    id = "atomic_radius",
                    title = "Trends in Atomic Radius",
                    intro = "Atomic radius is the size of an atom. It changes in a predictable way as you move across or down the periodic table.",
                    keyFacts = listOf(
                        "Atomic radius increases down a group as new shells are added.",
                        "Atomic radius decreases across a period as nuclear charge pulls electrons in.",
                        "Cations are smaller than their parent atoms.",
                        "Anions are larger than their parent atoms.",
                        "Trends help predict reactivity and bond lengths."
                    ),
                    examples = listOf(
                        "Cesium is one of the largest atoms; helium is one of the smallest.",
                        "Na⁺ is much smaller than Na because it has lost its outer shell."
                    ),
                    visual = Visual.Shells
                ),
                Subtopic(
                    id = "electronegativity",
                    title = "Electronegativity",
                    intro = "Electronegativity measures how strongly an atom in a bond pulls shared electrons toward itself. It controls bond polarity.",
                    keyFacts = listOf(
                        "Increases across a period; decreases down a group.",
                        "Fluorine is the most electronegative element (4.0 on Pauling's scale).",
                        "Cesium and francium are the least electronegative.",
                        "Big difference (>1.7) → ionic bond; small difference → polar or nonpolar covalent.",
                        "Drives why water is polar and oils are nonpolar."
                    ),
                    examples = listOf(
                        "In HCl, Cl pulls electrons more than H — the bond is polar.",
                        "In O–O, the atoms pull equally — nonpolar covalent."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "noble_gases",
                    title = "Noble Gases",
                    intro = "Group 18 elements are called noble gases because their outer shells are full, making them remarkably unreactive under normal conditions.",
                    keyFacts = listOf(
                        "All have a complete octet (helium has 2 valence electrons; the rest have 8).",
                        "They exist as single atoms (monatomic).",
                        "Used where inert atmospheres are needed.",
                        "Heavier ones (Kr, Xe) can form a few exotic compounds.",
                        "All glow with characteristic colors when electrified."
                    ),
                    examples = listOf(
                        "Neon glows orange-red in advertising signs.",
                        "Helium fills balloons because it is light and unreactive."
                    ),
                    visual = Visual.Shells
                )
            )
        ),
        // 3
        Topic(
            id = "chemical_bonds",
            title = "Chemical Bonds",
            description = "The forces that hold atoms together inside molecules and crystals.",
            accent = NeonViolet,
            cardVisual = Visual.Molecule,
            subtopics = listOf(
                Subtopic(
                    id = "ionic",
                    title = "Ionic Bonds",
                    intro = "An ionic bond forms when a metal transfers electrons to a nonmetal, creating oppositely charged ions that pull together strongly.",
                    keyFacts = listOf(
                        "Forms between elements with very different electronegativities.",
                        "Produces a crystal lattice, not isolated molecules.",
                        "Compounds tend to have high melting points.",
                        "Conducts electricity when molten or dissolved in water.",
                        "Often dissolves in polar solvents like water."
                    ),
                    examples = listOf(
                        "Na + Cl → Na⁺Cl⁻ (table salt).",
                        "Mg + O → Mg²⁺O²⁻."
                    ),
                    visual = Visual.BondIonic
                ),
                Subtopic(
                    id = "covalent",
                    title = "Covalent Bonds",
                    intro = "A covalent bond forms when two nonmetals share electrons rather than transferring them.",
                    keyFacts = listOf(
                        "Shared pair(s) of electrons hold the atoms together.",
                        "Single, double, and triple bonds are possible.",
                        "More shared pairs → shorter, stronger bond.",
                        "Most covalent compounds have low melting points.",
                        "Form discrete molecules with definite shapes."
                    ),
                    examples = listOf(
                        "H–H in H₂ shares one pair of electrons.",
                        "O=O in O₂ shares two pairs (double bond)."
                    ),
                    visual = Visual.BondCovalent
                ),
                Subtopic(
                    id = "metallic",
                    title = "Metallic Bonds",
                    intro = "In metals, positive ions sit in a lattice while their valence electrons are delocalized into a shared 'sea' that flows around them.",
                    keyFacts = listOf(
                        "Free electrons explain conductivity of heat and electricity.",
                        "The flexible sea allows metals to be bent and hammered without shattering.",
                        "Strength comes from electrostatic attraction across the whole lattice.",
                        "Metals are typically opaque and shiny because of free electrons.",
                        "Alloys are mixtures of metallic-bonded elements."
                    ),
                    examples = listOf(
                        "Copper conducts electricity through its electron sea.",
                        "Iron can be forged because its layers slide without breaking the bond."
                    ),
                    visual = Visual.BondMetallic
                ),
                Subtopic(
                    id = "polarity",
                    title = "Polarity",
                    intro = "When two bonded atoms share electrons unequally, the bond is polar — one side becomes slightly negative, the other slightly positive.",
                    keyFacts = listOf(
                        "Difference in electronegativity drives polarity.",
                        "A polar bond does not always make a polar molecule — geometry matters.",
                        "Polar molecules tend to dissolve in polar solvents.",
                        "Hydrogen bonds form between very polar bonds like O–H or N–H.",
                        "Polarity affects boiling point, viscosity, and solubility."
                    ),
                    examples = listOf(
                        "Water is polar; CO₂ has polar bonds but is overall nonpolar (linear).",
                        "Oil and water do not mix because of mismatched polarity."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "lewis",
                    title = "Lewis Structures",
                    intro = "A Lewis structure is a diagram that shows valence electrons as dots and bonding pairs as lines, helping predict how molecules look.",
                    keyFacts = listOf(
                        "Each line represents a shared pair of electrons.",
                        "Lone (non-bonding) pairs are shown as dots on one atom.",
                        "Most main-group atoms aim for an octet of 8 electrons.",
                        "Hydrogen aims for 2 electrons (a duet).",
                        "Formal charges help choose between possible structures."
                    ),
                    examples = listOf(
                        "Water: H–O–H with two lone pairs on oxygen.",
                        "CO₂: O=C=O with two lone pairs on each oxygen."
                    ),
                    visual = Visual.DoubleBond
                )
            )
        ),
        // 4
        Topic(
            id = "chemical_reactions",
            title = "Chemical Reactions",
            description = "Bonds break and reform — atoms regroup into new substances.",
            accent = NeonAmber,
            cardVisual = Visual.Reaction,
            subtopics = listOf(
                Subtopic(
                    id = "synthesis",
                    title = "Synthesis Reactions",
                    intro = "In a synthesis (combination) reaction two or more reactants come together to form a single, more complex product.",
                    keyFacts = listOf(
                        "General form: A + B → AB.",
                        "Often releases energy as heat or light.",
                        "Common between metals and nonmetals.",
                        "Can occur between two compounds, like an oxide and water.",
                        "Used to make many industrial products."
                    ),
                    examples = listOf(
                        "2 H₂ + O₂ → 2 H₂O.",
                        "CaO + H₂O → Ca(OH)₂."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "decomposition",
                    title = "Decomposition Reactions",
                    intro = "Decomposition is the reverse of synthesis — a single compound breaks down into two or more simpler substances, often with added heat or electricity.",
                    keyFacts = listOf(
                        "General form: AB → A + B.",
                        "Usually requires energy input.",
                        "Electrolysis is a way to drive decomposition with electricity.",
                        "Many carbonates decompose into oxides and CO₂.",
                        "Hydrogen peroxide decomposes spontaneously when catalyzed."
                    ),
                    examples = listOf(
                        "2 H₂O → 2 H₂ + O₂ (electrolysis).",
                        "CaCO₃ → CaO + CO₂ (heat)."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "single_replacement",
                    title = "Single Replacement",
                    intro = "A more reactive element kicks a less reactive element out of its compound, taking its place.",
                    keyFacts = listOf(
                        "General form: A + BC → AC + B.",
                        "Requires A to be more reactive than B.",
                        "Activity series predicts which metals can replace which.",
                        "Halogens follow their own activity order (F > Cl > Br > I).",
                        "Forms a new compound and a free element."
                    ),
                    examples = listOf(
                        "Zn + CuSO₄ → ZnSO₄ + Cu.",
                        "Cl₂ + 2 NaBr → 2 NaCl + Br₂."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "double_replacement",
                    title = "Double Replacement",
                    intro = "Two ionic compounds swap partners, often producing a precipitate, a gas, or water.",
                    keyFacts = listOf(
                        "General form: AB + CD → AD + CB.",
                        "Driving force: precipitate, gas, or weak/un-ionized product.",
                        "Common in aqueous solutions.",
                        "Solubility rules predict whether a precipitate forms.",
                        "Acid–base neutralizations are double-replacement reactions."
                    ),
                    examples = listOf(
                        "AgNO₃ + NaCl → AgCl(s) + NaNO₃.",
                        "HCl + NaOH → NaCl + H₂O."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "balancing",
                    title = "Balancing Equations",
                    intro = "Atoms cannot be created or destroyed in a chemical change. Balancing makes sure each element appears the same number of times on both sides of the arrow.",
                    keyFacts = listOf(
                        "Use coefficients in front of formulas, never subscripts inside them.",
                        "Balance metals first, then nonmetals, then H, then O.",
                        "Polyatomic ions can sometimes be balanced as a single unit.",
                        "Total charge must also balance in net ionic equations.",
                        "Balanced equations make stoichiometry possible."
                    ),
                    examples = listOf(
                        "C₃H₈ + 5 O₂ → 3 CO₂ + 4 H₂O.",
                        "4 Fe + 3 O₂ → 2 Fe₂O₃."
                    ),
                    visual = Visual.Reaction
                )
            )
        ),
        // 5
        Topic(
            id = "acids_bases",
            title = "Acids & Bases",
            description = "Proton donors and acceptors — and how they neutralize each other.",
            accent = NeonPink,
            cardVisual = Visual.PhScale,
            subtopics = listOf(
                Subtopic(
                    id = "ph_scale",
                    title = "pH Scale",
                    intro = "The pH scale measures how acidic or basic a solution is by tracking the concentration of hydrogen ions, H⁺.",
                    keyFacts = listOf(
                        "pH = −log[H⁺].",
                        "pH 7 is neutral; below 7 is acidic; above 7 is basic.",
                        "The scale is logarithmic — each unit is a 10× change in [H⁺].",
                        "pH + pOH = 14 in water at 25 °C.",
                        "Strong acids and bases have extreme pH values."
                    ),
                    examples = listOf(
                        "Lemon juice ≈ pH 2; pure water = pH 7; bleach ≈ pH 13.",
                        "Stomach acid ≈ pH 1.5–2."
                    ),
                    visual = Visual.PhScale
                ),
                Subtopic(
                    id = "strong_weak_acids",
                    title = "Strong vs Weak Acids",
                    intro = "Strong acids ionize completely in water, while weak acids only partly ionize and exist mostly as molecules.",
                    keyFacts = listOf(
                        "Strong acids: HCl, HBr, HI, HNO₃, HClO₄, H₂SO₄.",
                        "Weak acids stay mostly molecular in water.",
                        "Strength refers to ionization, not concentration.",
                        "Ka measures acid strength — bigger Ka = stronger acid.",
                        "Strong bases include NaOH, KOH and group 2 hydroxides."
                    ),
                    examples = listOf(
                        "HCl in water → H⁺ + Cl⁻ (essentially 100%).",
                        "Acetic acid (vinegar) is weak — only ~1% ionizes."
                    ),
                    visual = Visual.PhScale
                ),
                Subtopic(
                    id = "neutralization",
                    title = "Neutralization",
                    intro = "When an acid reacts with a base, H⁺ and OH⁻ combine to form water, and the leftover ions form a salt.",
                    keyFacts = listOf(
                        "Acid + base → salt + water.",
                        "Strong acid + strong base → neutral pH 7.",
                        "Net ionic equation often: H⁺ + OH⁻ → H₂O.",
                        "Used to titrate unknown concentrations.",
                        "Heat is released — neutralization is exothermic."
                    ),
                    examples = listOf(
                        "HCl + NaOH → NaCl + H₂O.",
                        "H₂SO₄ + 2 KOH → K₂SO₄ + 2 H₂O."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "indicators",
                    title = "Indicators",
                    intro = "Indicators are dyes that change color depending on the pH of the solution. They help detect endpoints in titrations.",
                    keyFacts = listOf(
                        "Each indicator has a characteristic color-change range.",
                        "Litmus: red in acid, blue in base.",
                        "Phenolphthalein: colorless in acid, pink in base.",
                        "Universal indicator covers a wide pH range with many colors.",
                        "Indicators are themselves weak acids or bases."
                    ),
                    examples = listOf(
                        "Bromothymol blue is yellow at pH < 6 and blue at pH > 7.6.",
                        "Phenolphthalein turns pink near pH 8.3."
                    ),
                    visual = Visual.PhScale
                ),
                Subtopic(
                    id = "buffers",
                    title = "Buffers",
                    intro = "A buffer resists changes in pH when small amounts of acid or base are added. It uses a balanced pair of weak acid and conjugate base.",
                    keyFacts = listOf(
                        "Made of a weak acid + its conjugate base (or vice versa).",
                        "Absorbs added H⁺ or OH⁻ with little pH change.",
                        "Henderson–Hasselbalch: pH = pKa + log([A⁻]/[HA]).",
                        "Critical for biological systems.",
                        "Buffer capacity is highest near the pKa of the weak acid."
                    ),
                    examples = listOf(
                        "Blood is buffered by H₂CO₃ / HCO₃⁻ near pH 7.4.",
                        "Acetic acid + sodium acetate forms a common lab buffer."
                    ),
                    visual = Visual.PhScale
                )
            )
        ),
        // 6
        Topic(
            id = "organic",
            title = "Organic Chemistry",
            description = "The chemistry of carbon and the molecules of life.",
            accent = AcidGreen,
            cardVisual = Visual.DoubleBond,
            subtopics = listOf(
                Subtopic(
                    id = "hydrocarbons",
                    title = "Hydrocarbons",
                    intro = "Hydrocarbons are molecules made of only carbon and hydrogen. They are the foundation of fuels, plastics, and most organic chemistry.",
                    keyFacts = listOf(
                        "Alkanes have only single C–C bonds (CₙH₂ₙ₊₂).",
                        "Alkenes have at least one C=C double bond.",
                        "Alkynes have at least one C≡C triple bond.",
                        "Aromatics like benzene have ring structures with delocalized electrons.",
                        "Burning hydrocarbons completely yields CO₂ and H₂O."
                    ),
                    examples = listOf(
                        "Methane (CH₄) is the simplest alkane.",
                        "Ethene (C₂H₄) is the simplest alkene."
                    ),
                    visual = Visual.DoubleBond
                ),
                Subtopic(
                    id = "functional_groups",
                    title = "Functional Groups",
                    intro = "A functional group is a small, characteristic atom arrangement inside a molecule that controls most of its chemistry.",
                    keyFacts = listOf(
                        "Same group → similar reactivity, even in different molecules.",
                        "Common groups: –OH (alcohol), –COOH (acid), –NH₂ (amine), C=O (carbonyl).",
                        "Halogens (–F, –Cl, –Br, –I) form haloalkanes.",
                        "Esters give many fruits their smell.",
                        "Functional groups are the 'verbs' of organic chemistry."
                    ),
                    examples = listOf(
                        "Ethanol has the –OH alcohol group.",
                        "Acetic acid has the –COOH carboxylic-acid group."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "alcohols",
                    title = "Alcohols",
                    intro = "Alcohols are organic molecules with a hydroxyl (–OH) group bonded to a carbon. They are common solvents and intermediates.",
                    keyFacts = listOf(
                        "General formula: R–OH.",
                        "Polar because of the O–H bond — many dissolve in water.",
                        "Hydrogen bond, giving higher boiling points than alkanes.",
                        "Can be oxidized to aldehydes/ketones, then to carboxylic acids.",
                        "Classified as primary, secondary, or tertiary."
                    ),
                    examples = listOf(
                        "Methanol (CH₃OH) — used as a fuel and solvent.",
                        "Ethanol (C₂H₅OH) — present in alcoholic drinks and sanitizer."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "carboxylic",
                    title = "Carboxylic Acids",
                    intro = "Carboxylic acids contain the –COOH group. They are weak acids and play key roles in biology and industry.",
                    keyFacts = listOf(
                        "General formula: R–COOH.",
                        "Lose H⁺ from the –OH, leaving –COO⁻.",
                        "React with alcohols to form esters (esterification).",
                        "Higher boiling points than similar alcohols.",
                        "Found widely in nature, from vinegar to fatty acids."
                    ),
                    examples = listOf(
                        "Acetic acid (CH₃COOH) — main acid in vinegar.",
                        "Citric acid — sour taste of citrus fruit."
                    ),
                    visual = Visual.DoubleBond
                ),
                Subtopic(
                    id = "polymers",
                    title = "Polymers",
                    intro = "Polymers are huge molecules made by linking many small repeating units called monomers.",
                    keyFacts = listOf(
                        "Addition polymerization: monomers add across a double bond.",
                        "Condensation polymerization: monomers join with loss of a small molecule (often water).",
                        "Synthetic polymers include plastics, rubbers, and fibers.",
                        "Natural polymers include proteins, DNA, cellulose, starch.",
                        "Properties depend on chain length and cross-linking."
                    ),
                    examples = listOf(
                        "Polyethylene is made from ethene monomers (–CH₂–CH₂–)ₙ.",
                        "Nylon is a condensation polymer used in fibers."
                    ),
                    visual = Visual.Molecule
                )
            )
        ),
        // 7
        Topic(
            id = "stoichiometry",
            title = "Stoichiometry",
            description = "Counting atoms, moles, and grams to predict how much reacts.",
            accent = ElectricCyan,
            cardVisual = Visual.Reaction,
            subtopics = listOf(
                Subtopic(
                    id = "mole",
                    title = "Mole Concept",
                    intro = "A mole is a counting unit chemists use because individual atoms are far too small to weigh one at a time.",
                    keyFacts = listOf(
                        "1 mole = 6.022 × 10²³ particles (Avogadro's number).",
                        "Works for atoms, ions, molecules, electrons — anything.",
                        "Bridges the microscopic (atoms) to the macroscopic (grams).",
                        "Number of moles = mass / molar mass.",
                        "Molar volume of an ideal gas at STP ≈ 22.4 L."
                    ),
                    examples = listOf(
                        "1 mole of water has 6.022 × 10²³ molecules and weighs about 18 g.",
                        "1 mole of carbon has the same number of atoms as 1 mole of iron."
                    ),
                    visual = Visual.Atom
                ),
                Subtopic(
                    id = "molar_mass",
                    title = "Molar Mass",
                    intro = "Molar mass tells you how many grams correspond to one mole of a substance, computed by adding up the atomic masses in the formula.",
                    keyFacts = listOf(
                        "Units: g/mol.",
                        "Found by summing atomic masses from the periodic table.",
                        "Used to convert between grams and moles.",
                        "Molar mass of a compound = sum over each atom × its count.",
                        "Vital for stoichiometric calculations."
                    ),
                    examples = listOf(
                        "Molar mass of H₂O = 2(1.01) + 16.00 ≈ 18.02 g/mol.",
                        "Molar mass of NaCl ≈ 58.44 g/mol."
                    ),
                    visual = Visual.PeriodicCell
                ),
                Subtopic(
                    id = "mole_ratios",
                    title = "Mole Ratios",
                    intro = "The coefficients in a balanced equation give a ratio of moles between substances, letting you predict how much product forms from a given amount of reactant.",
                    keyFacts = listOf(
                        "Coefficients are mole ratios, not gram ratios.",
                        "Use mole ratios to convert moles of A to moles of B.",
                        "Pair them with molar masses to convert between grams.",
                        "Same ratios apply to volumes of gases under the same conditions.",
                        "Foundation of all reaction-amount calculations."
                    ),
                    examples = listOf(
                        "In 2 H₂ + O₂ → 2 H₂O, the ratio H₂:O₂:H₂O is 2:1:2.",
                        "From 4 mol N₂, you can form 8 mol NH₃ (with excess H₂)."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "limiting",
                    title = "Limiting Reactants",
                    intro = "When reactants are not in their exact stoichiometric ratio, one runs out first. That reactant is the limiting one and decides how much product can be made.",
                    keyFacts = listOf(
                        "Calculate moles of product each reactant could give; the smallest wins.",
                        "The other reactant(s) are in excess.",
                        "Excess reactant can be calculated by subtraction.",
                        "Theoretical yield is based on the limiting reactant.",
                        "Common in lab synthesis where you mix non-ideal amounts."
                    ),
                    examples = listOf(
                        "2 H₂ + O₂ → 2 H₂O: with 4 mol H₂ and 1 mol O₂, O₂ is limiting.",
                        "Bread analogy: 4 slices + 1 piece of cheese → only 1 sandwich."
                    ),
                    visual = Visual.Reaction
                ),
                Subtopic(
                    id = "percent_yield",
                    title = "Percent Yield",
                    intro = "In real labs, reactions almost never produce 100% of the predicted product. Percent yield compares the actual amount obtained to the theoretical amount.",
                    keyFacts = listOf(
                        "Percent yield = (actual / theoretical) × 100%.",
                        "Always less than 100% in real life.",
                        "Lost yield comes from side reactions, transfer losses, and incomplete reactions.",
                        "Theoretical yield is based on the limiting reactant.",
                        "Industrial processes optimize yield to control costs."
                    ),
                    examples = listOf(
                        "If theoretical yield = 10 g and actual = 8 g, percent yield = 80%.",
                        "A pharmaceutical step with 60% yield over 5 steps gives ≈ 7.8% overall."
                    ),
                    visual = Visual.Reaction
                )
            )
        ),
        // 8
        Topic(
            id = "thermo",
            title = "Thermochemistry",
            description = "How energy moves into and out of chemical reactions.",
            accent = NeonRed,
            cardVisual = Visual.EnergyExo,
            subtopics = listOf(
                Subtopic(
                    id = "exothermic",
                    title = "Exothermic Reactions",
                    intro = "An exothermic reaction releases heat to its surroundings, making them feel warmer.",
                    keyFacts = listOf(
                        "Products have lower energy than reactants.",
                        "ΔH is negative.",
                        "Combustion, neutralization, and many oxidations are exothermic.",
                        "Heat is a product on the reactant side of the energy diagram.",
                        "Surroundings get warmer — temperature rises."
                    ),
                    examples = listOf(
                        "Burning methane: CH₄ + 2 O₂ → CO₂ + 2 H₂O + heat.",
                        "Hand warmers releasing heat from iron oxidation."
                    ),
                    visual = Visual.EnergyExo
                ),
                Subtopic(
                    id = "endothermic",
                    title = "Endothermic Reactions",
                    intro = "An endothermic reaction absorbs heat from its surroundings, making them feel cooler.",
                    keyFacts = listOf(
                        "Products have higher energy than reactants.",
                        "ΔH is positive.",
                        "Photosynthesis is endothermic — sunlight is the energy input.",
                        "Many decomposition and dissolution reactions are endothermic.",
                        "Surroundings get cooler — temperature drops."
                    ),
                    examples = listOf(
                        "Cold packs use ammonium-nitrate dissolution to absorb heat.",
                        "Photosynthesis: 6 CO₂ + 6 H₂O + light → C₆H₁₂O₆ + 6 O₂."
                    ),
                    visual = Visual.EnergyEndo
                ),
                Subtopic(
                    id = "enthalpy",
                    title = "Enthalpy",
                    intro = "Enthalpy (H) is the heat content of a system at constant pressure. The change in enthalpy, ΔH, tells you how much heat is exchanged in a reaction.",
                    keyFacts = listOf(
                        "ΔH < 0 → exothermic; ΔH > 0 → endothermic.",
                        "Standard enthalpy of formation (ΔH°f) refers to forming 1 mole of substance from elements in standard states.",
                        "Hess's law: total enthalpy change is the sum of intermediate steps.",
                        "Bond breaking is endothermic; bond forming is exothermic.",
                        "Units: typically kJ/mol."
                    ),
                    examples = listOf(
                        "ΔHcombustion of methane ≈ −890 kJ/mol.",
                        "Hess's law lets you calculate ΔH from a series of known reactions."
                    ),
                    visual = Visual.EnergyExo
                ),
                Subtopic(
                    id = "energy_diagrams",
                    title = "Energy Diagrams",
                    intro = "An energy diagram plots energy along the path from reactants to products, showing the activation energy barrier and the overall ΔH.",
                    keyFacts = listOf(
                        "Activation energy (Ea) is the hill between reactants and products.",
                        "Catalysts lower the activation energy.",
                        "Exothermic: products dip below reactants.",
                        "Endothermic: products rise above reactants.",
                        "Transition state sits at the top of the hill."
                    ),
                    examples = listOf(
                        "Catalysts in catalytic converters lower Ea for harmful gases.",
                        "Enzyme-catalyzed reactions in cells follow the same idea."
                    ),
                    visual = Visual.EnergyEndo
                ),
                Subtopic(
                    id = "calorimetry",
                    title = "Calorimetry",
                    intro = "Calorimetry measures the heat released or absorbed by a reaction by tracking temperature change in a known mass of substance.",
                    keyFacts = listOf(
                        "q = m c ΔT (heat = mass × specific heat × temperature change).",
                        "Specific heat (c) is energy needed to raise 1 g by 1 °C.",
                        "A bomb calorimeter measures combustion at constant volume.",
                        "Coffee-cup calorimetry approximates constant pressure.",
                        "Important for nutrition (food energy) and engineering."
                    ),
                    examples = listOf(
                        "Specific heat of water = 4.18 J/(g·°C).",
                        "Burning a peanut warms a water bath in a school calorimeter."
                    ),
                    visual = Visual.EnergyExo
                )
            )
        ),
        // 9
        Topic(
            id = "electro",
            title = "Electrochemistry",
            description = "Reactions driven by — or producing — electricity.",
            accent = NeonViolet,
            cardVisual = Visual.Electron,
            subtopics = listOf(
                Subtopic(
                    id = "oxidation",
                    title = "Oxidation",
                    intro = "Oxidation is the loss of electrons by a substance. The oxidation number of the species increases.",
                    keyFacts = listOf(
                        "OIL: Oxidation Is Loss (of electrons).",
                        "Oxidation number rises during oxidation.",
                        "Substance that is oxidized acts as the reducing agent.",
                        "Oxygen often (but not always) drives oxidation.",
                        "Common in corrosion and combustion."
                    ),
                    examples = listOf(
                        "Iron rusting: Fe → Fe³⁺ + 3 e⁻.",
                        "Magnesium burning: Mg → Mg²⁺ + 2 e⁻."
                    ),
                    visual = Visual.Electron
                ),
                Subtopic(
                    id = "reduction",
                    title = "Reduction",
                    intro = "Reduction is the gain of electrons by a substance. The oxidation number of the species decreases.",
                    keyFacts = listOf(
                        "RIG: Reduction Is Gain (of electrons).",
                        "Oxidation number drops during reduction.",
                        "Substance that is reduced acts as the oxidizing agent.",
                        "Many metal ions are reduced to neutral metals in extraction.",
                        "Always paired with an oxidation — no free electrons."
                    ),
                    examples = listOf(
                        "Cu²⁺ + 2 e⁻ → Cu (electroplating).",
                        "Cl₂ + 2 e⁻ → 2 Cl⁻."
                    ),
                    visual = Visual.Electron
                ),
                Subtopic(
                    id = "redox",
                    title = "Redox Reactions",
                    intro = "A redox (reduction–oxidation) reaction is a coupled pair where one substance is oxidized while another is reduced.",
                    keyFacts = listOf(
                        "Electrons leave the reducing agent and join the oxidizing agent.",
                        "Both half-reactions must balance in atoms and charge.",
                        "Combustion, photosynthesis, and respiration are redox.",
                        "Tracking oxidation numbers helps spot what is oxidized/reduced.",
                        "Net e⁻ transferred is the same on both sides."
                    ),
                    examples = listOf(
                        "Zn + Cu²⁺ → Zn²⁺ + Cu.",
                        "2 Na + Cl₂ → 2 NaCl."
                    ),
                    visual = Visual.Electron
                ),
                Subtopic(
                    id = "galvanic",
                    title = "Galvanic Cells",
                    intro = "A galvanic (voltaic) cell turns a spontaneous redox reaction into electricity by separating the two half-reactions and connecting them through a wire.",
                    keyFacts = listOf(
                        "Anode: oxidation occurs (negative terminal in a galvanic cell).",
                        "Cathode: reduction occurs (positive terminal).",
                        "Electrons flow from anode to cathode through the external wire.",
                        "Salt bridge balances charge by ion flow.",
                        "Cell EMF (E°) = E°cathode − E°anode."
                    ),
                    examples = listOf(
                        "Daniell cell: Zn anode, Cu²⁺/Cu cathode.",
                        "Common AA batteries are galvanic cells."
                    ),
                    visual = Visual.Electron
                ),
                Subtopic(
                    id = "electrolysis",
                    title = "Electrolysis",
                    intro = "Electrolysis uses electricity to drive a non-spontaneous redox reaction in the opposite direction.",
                    keyFacts = listOf(
                        "Requires an external power source.",
                        "Anode is positive in an electrolytic cell.",
                        "Cathode is negative in an electrolytic cell.",
                        "Used to extract reactive metals like Al and Na.",
                        "Also used in electroplating and refining metals."
                    ),
                    examples = listOf(
                        "Electrolysis of molten NaCl gives Na metal and Cl₂ gas.",
                        "Aluminum is produced industrially by the Hall-Héroult process."
                    ),
                    visual = Visual.Electron
                )
            )
        ),
        // 10
        Topic(
            id = "solutions",
            title = "Solutions",
            description = "Mixing substances at the molecular level.",
            accent = ElectricCyan,
            cardVisual = Visual.Molecule,
            subtopics = listOf(
                Subtopic(
                    id = "solute_solvent",
                    title = "Solute & Solvent",
                    intro = "A solution is a homogeneous mixture: the solute is the dissolved substance, and the solvent is what dissolves it.",
                    keyFacts = listOf(
                        "Solute is usually present in smaller amount.",
                        "Solvent is usually present in larger amount.",
                        "Solutions are uniform at the molecular level.",
                        "Solvent state usually decides the state of the solution.",
                        "Like dissolves like — polar solvents dissolve polar solutes."
                    ),
                    examples = listOf(
                        "Sugar in tea: sugar = solute, water = solvent.",
                        "Air is a gaseous solution of oxygen and other gases in nitrogen."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "concentration",
                    title = "Concentration",
                    intro = "Concentration tells you how much solute is in a given amount of solution. Several units are used depending on context.",
                    keyFacts = listOf(
                        "Mass percent: (mass solute / mass solution) × 100%.",
                        "Mole fraction: moles of one component / total moles.",
                        "Parts per million (ppm) for very dilute mixtures.",
                        "Higher concentration → more solute per unit of solution.",
                        "Concentration is intensive — it does not depend on amount taken."
                    ),
                    examples = listOf(
                        "5% saline solution = 5 g salt per 100 g solution.",
                        "Many drinking-water limits are stated in ppm."
                    ),
                    visual = Visual.PhScale
                ),
                Subtopic(
                    id = "molarity",
                    title = "Molarity",
                    intro = "Molarity (M) is moles of solute per liter of solution. It is the most common concentration unit in chemistry labs.",
                    keyFacts = listOf(
                        "Molarity = moles of solute / liters of solution.",
                        "Units: mol/L, often shortened to M.",
                        "Easy to use because volumes are easy to measure.",
                        "Volume of solution depends on temperature, slightly affecting M.",
                        "Used in titrations and stoichiometry of solutions."
                    ),
                    examples = listOf(
                        "0.5 mol NaOH in 1 L solution → 0.5 M NaOH.",
                        "Doubling moles in the same volume doubles molarity."
                    ),
                    visual = Visual.PhScale
                ),
                Subtopic(
                    id = "dilution",
                    title = "Dilution",
                    intro = "Dilution is the process of adding more solvent so the same amount of solute occupies a larger volume, lowering its concentration.",
                    keyFacts = listOf(
                        "Number of moles of solute does not change during dilution.",
                        "M₁V₁ = M₂V₂.",
                        "Used to prepare working concentrations from concentrated stocks.",
                        "Always add concentrated solution to water, not the other way (especially with strong acids).",
                        "Standard step in serial-dilution experiments."
                    ),
                    examples = listOf(
                        "10 mL of 1 M HCl diluted to 100 mL → 0.1 M HCl.",
                        "Halving the volume by adding solvent halves the molarity if no solvent is removed."
                    ),
                    visual = Visual.Molecule
                ),
                Subtopic(
                    id = "solubility",
                    title = "Solubility",
                    intro = "Solubility is the maximum amount of solute that can dissolve in a fixed amount of solvent at a given temperature.",
                    keyFacts = listOf(
                        "Saturated solution is at its solubility limit.",
                        "Most solid solubilities increase with temperature.",
                        "Most gas solubilities decrease with temperature.",
                        "Pressure strongly affects gas solubility (Henry's law).",
                        "Polar solvents dissolve polar solutes; nonpolar dissolves nonpolar."
                    ),
                    examples = listOf(
                        "Sugar dissolves more in hot water than cold.",
                        "Carbonated drinks lose CO₂ as they warm up."
                    ),
                    visual = Visual.PhScale
                )
            )
        )
    )

    fun byId(id: String): Topic? = all.firstOrNull { it.id == id }
    fun subtopic(topicId: String, subId: String): Subtopic? =
        byId(topicId)?.subtopics?.firstOrNull { it.id == subId }
}
