# Path to the FXML file which defines the user interface
fxml_file_path = "../fxml/HomePage.fxml"

# Read the entire content of the FXML file
with open(fxml_file_path, 'r') as file:
    file_content = file.read()

# Split the content into lines and remove any trailing whitespace from each line
processed_lines = [line.strip() for line in file_content.split("\n")]

# Extract lines that contain the 'fx:id' attribute
id_lines = [line for line in processed_lines if "fx:id" in line]

# Parse the lines to extract the type of the component and its fx:id
parsed_ids = []
for line in id_lines:
    # Extract the component type, which is the first word after the opening angle bracket '<'
    space_index = line.find(" ")
    component_type = line[1:space_index]  # Skip the '<' at the start of the tag

    # Extract the fx:id value
    id_start_index = line.find("fx:id")
    id_substring = line[id_start_index:]
    id_space_index = id_substring.find(" ")
    fx_id = id_substring[:id_space_index]

    # Extract the id value from the fx:id string
    quote_index = fx_id.find('"')
    component_id = fx_id[quote_index + 1:fx_id.rfind('"')]  # Correctly handle the closing quote

    parsed_ids.append((component_type, component_id))

# Prepare and write the individual IDs to a file
individuals_output_path = fxml_file_path.rstrip('.fxml') + "IDs.txt"
with open(individuals_output_path, 'w') as file:
    for type_id_pair in parsed_ids:
        individual_entry = f"@FXML\n{type_id_pair[0]} {type_id_pair[1]};\n"
        file.write(individual_entry)

# Create a grouped version and write it to a file
group_dict = {}
for type_id_pair in parsed_ids:
    if type_id_pair[0] not in group_dict:
        group_dict[type_id_pair[0]] = []
    group_dict[type_id_pair[0]].append(type_id_pair[1])

grouped_output_path = fxml_file_path.rstrip('.fxml') + "IDsGrouped.txt"
with open(grouped_output_path, 'w') as file:
    for component_type, ids in group_dict.items():
        grouped_entry = f"@FXML\n{component_type} " + ", ".join(ids) + ";\n"
        file.write(grouped_entry)
