import simpy
import random
import statistics


RAM = 100
CPU = 1
LIMIT = 1

class Process:
    def __init__(self, env, name, ram, cpu, completion_times):
        self.env = env
        self.name = name
        self.ram = ram
        self.cpu = cpu
        self.memory_required = random.randint(1, 10)
        self.instructions_total = random.randint(1, 10)
        self.instructions_remaining = self.instructions_total
        self.completion_times = completion_times

    def run(self):
        while self.instructions_remaining > 0:
            with self.cpu.request() as req:
                yield req
                if self.instructions_remaining < LIMIT:
                    cpu_time = self.instructions_remaining / CPU
                    self.instructions_remaining = 0
                else:
                    cpu_time = LIMIT / CPU
                    self.instructions_remaining -= LIMIT

                yield self.env.timeout(cpu_time)

                if self.instructions_remaining == 0:
                    self.completion_times.append(self.env.now)
                else:
                    io_wait = random.randint(1, 2)
                    if io_wait == 1:
                        yield self.env.timeout(1)

def genprocess(env, ram, cpu, completion_times):
    global process_id
    process_id = 0
    while True:
        process_id += 1
        p = Process(env, f"Proceso-{process_id}", ram, cpu, completion_times)
        env.process(p.run())
        yield env.timeout(random.expovariate(1.0 / 10))

def main(processes):
    random.seed(41)
    env = simpy.Environment()

    ram = simpy.Container(env, init=RAM, capacity=RAM)
    cpu = simpy.Resource(env, capacity=1)

    completion_times = []
    env.process(genprocess(env, ram, cpu, completion_times))

    env.run(until=processes * 2)

    time = statistics.mean(completion_times)
    standard_deviation = statistics.stdev(completion_times)

    print(f"Número de procesos: {processes}")
    print(f"Tiempo: {time}")
    print(f"Desviación estándar: {standard_deviation}")


main(25)
main(50)
main(100)
main(150)
main(200)
