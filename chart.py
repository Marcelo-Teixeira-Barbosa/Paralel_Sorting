import pandas as pd
import matplotlib.pyplot as plt
from tkinter import *
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.colors import ListedColormap

# Function to update the chart based on the selected sorting algorithm
def update_chart(sort_algorithm):
    ax.clear()  # Clear the previous plot
    colormap = plt.get_cmap('tab10', len(unique_titles))  # Define colormap with unique color for each title
    for title, color in zip(unique_titles, colormap.colors):
        filtered_data = avg_df[(avg_df['sort'] == sort_algorithm) & (avg_df['title'] == title)]
        ax.plot(filtered_data['size'], filtered_data['exectime'], marker='o', linestyle='-', label=title, color=color)
    ax.set_title(f'Execution Time vs Size for {sort_algorithm} Sort')
    ax.set_xlabel('Size')
    ax.set_ylabel('Execution Time')
    ax.grid(True)
    ax.legend(loc='upper left', fontsize='small', framealpha=0.5, title="Titles")  # Adjust legend position and transparency
    canvas.draw()

# Read the CSV file
df = pd.read_csv('sorting.csv')

# Group by 'title', 'type', 'size', and 'sort', and calculate the average 'exectime' for each group
avg_df = df.groupby(['title', 'type', 'size', 'sort']).agg({'exectime': 'mean'}).reset_index()

# Get unique titles
unique_titles = avg_df['title'].unique()

# Create a Tkinter window
root = Tk()
root.title('Sorting Algorithm Visualization')

# Create matplotlib figure and canvas
fig = plt.Figure(figsize=(8, 6))
ax = fig.add_subplot(111)
canvas = FigureCanvasTkAgg(fig, master=root)
canvas.get_tk_widget().pack(side=TOP, fill=BOTH, expand=1)

# Create a frame to hold the buttons
button_frame = Frame(root)
button_frame.pack(side=BOTTOM)

# Create buttons for each sorting algorithm with increased size
button_width = 15
button_height = 2
insertion_button = Button(button_frame, text="Insertion Sort", command=lambda: update_chart('insertion'), width=button_width, height=button_height)
insertion_button.pack(side=LEFT, padx=5, pady=5)

quick_button = Button(button_frame, text="Quick Sort", command=lambda: update_chart('quick'), width=button_width, height=button_height)
quick_button.pack(side=LEFT, padx=5, pady=5)

bubble_button = Button(button_frame, text="Bubble Sort", command=lambda: update_chart('bubble'), width=button_width, height=button_height)
bubble_button.pack(side=LEFT, padx=5, pady=5)

merge_button = Button(button_frame, text="Merge Sort", command=lambda: update_chart('merge'), width=button_width, height=button_height)
merge_button.pack(side=LEFT, padx=5, pady=5)

# Start the Tkinter event loop
root.mainloop()
